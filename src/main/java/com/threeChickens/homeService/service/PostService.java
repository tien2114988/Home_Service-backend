package com.threeChickens.homeService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.threeChickens.homeService.dto.bankHub.CancelPostDto;
import com.threeChickens.homeService.dto.post.*;
import com.threeChickens.homeService.dto.rate.CreateRateDto;
import com.threeChickens.homeService.dto.takePost.CreateTakePostDto;
import com.threeChickens.homeService.dto.takePost.FreelancerTakeDto;
import com.threeChickens.homeService.dto.takePost.TakePostDto;
import com.threeChickens.homeService.dto.workSchedule.GetWorkScheduleDto;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.middleware.UserMiddleware;
import com.threeChickens.homeService.repository.*;
import com.threeChickens.homeService.utils.FileUploadUtil;
import com.threeChickens.homeService.utils.FirebaseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private FreelancerTakePostRepository freelancerTakePostRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FreelancerWorkRepository freelancerWorkRepository;

    @Autowired
    private UserMiddleware userMiddleware;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;
    @Autowired
    private FirebaseUtil firebaseUtil;

    @Autowired
    private UserNotificationRepository userNotificationRepository;
    @Autowired
    private UserRepository userRepository;


    public Post getPostById(String id, boolean isCheck){
        if(isCheck){
            return postRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                    () -> new AppException(StatusCode.POST_NOT_FOUND)
            );
        }else{
            return postRepository.findByIdAndDeletedIsFalse(id).orElse(null);
        }
    }

    public GetPostDto uploadImages(String id, String type, MultipartFile[] images){
        Post post = getPostById(id, true);
        if(post.getWorkSchedules().size() <= post.getNumOfWorkedDay()){
            throw new AppException(StatusCode.WORK_SCHEDULE_NOT_FOUND);
        }
        WorkSchedule workSchedule = post.getWorkSchedules().stream().sorted(Comparator.comparing(WorkSchedule::getDate)).toList().get(post.getNumOfWorkedDay());

        Set<WorkScheduleImage> workScheduleImages = new HashSet<>();
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(images).forEach((image) -> {
            String filePath = fileUploadUtil.saveImage(image, type + "_" + id + "_" + post.getNumOfWorkedDay() + "_" + i.getAndIncrement());
            WorkScheduleImage workScheduleImage = WorkScheduleImage.builder().link(filePath).isStart(Objects.equals(type, "start")).workSchedule(workSchedule).build();
            workScheduleImages.add(workScheduleImage);
        });
        workSchedule.setImages(workScheduleImages);
        Time now = new Time(System.currentTimeMillis());
        if(Objects.equals(type, "start")){
            if(post.getNumOfWorkedDay() == 0){
                post.setStatus(PostStatus.DOING);
            }
            workSchedule.setStatus(WorkScheduleStatus.DOING);
            workSchedule.setStartTime(now);
        }else{
            post.setNumOfWorkedDay(post.getNumOfWorkedDay() + 1);
            if(post.getNumOfWorkedDay() == post.getTotalWorkDay()){
                post.setStatus(PostStatus.COMPLETED);
            }
            workSchedule.setStatus(WorkScheduleStatus.COMPLETED);
            workSchedule.setEndTime(now);
            if(post.getPaymentType() == PaymentType.QR){
                User customer = post.getCustomer();
                customer.setBalance(customer.getBalance() - post.getPrice());
                userRepository.save(customer);
                int pay = (int) Math.floor(post.getPrice()*0.9/post.getTotalFreelancer());
                List<User> freelancers = post.getFreelancerTakePosts().stream().map(FreelancerTakePost::getFreelancer).toList();
                freelancers.forEach(
                        freelancer -> {
                            freelancer.setBalance(freelancer.getBalance() + pay);
                            userRepository.save(freelancer);
                        }
                );
            }
        }
        Post finalPost = postRepository.save(post);
        workScheduleRepository.save(workSchedule);
        return modelMapper.map(finalPost, GetPostDto.class);
    }

    public GetPostDto rate(String id, List<CreateRateDto> createRateDtos){
        Post post = getPostById(id, true);

        Set<Rate> rates = createRateDtos.stream().map(
                createRateDto -> {
                    Rate rate = modelMapper.map(createRateDto, Rate.class);
                    FreelancerWorkService freelancerWorkService = freelancerWorkRepository.findByWorkIdAndFreelancerId(post.getWork().getId(), createRateDto.getFreelancerId()).orElseThrow(
                            ()-> new AppException(StatusCode.FREELANCER_WORK_NOT_FOUND)
                    );
                    rate.setPost(post);
                    rate.setFreelancerWorkService(freelancerWorkService);
                    return rate;
                }
        ).collect(Collectors.toSet());

        post.setRates(rates);

        return modelMapper.map(post, GetPostDto.class);
    }

    public TakePostDto takePost(String id, CreateTakePostDto createTakePostDto) throws JsonProcessingException {
        Post post = getPostById(id, true);

        User freelancer = userService.getByIdAndRole(createTakePostDto.getFreelancerId(), UserRole.FREELANCER);

        if(post.getStatus() != PostStatus.INITIAL){
            throw new AppException(StatusCode.POST_NOT_FOUND);
        }

        if(post.getNumOfFreelancer() >= post.getTotalFreelancer()){
            throw new AppException(StatusCode.POST_FULL_FREELANCER);
        }

        FreelancerTakePost freelancerTakePost = freelancerTakePostRepository.findByFreelancerAndPost(freelancer, post).orElse(
                FreelancerTakePost.builder().freelancer(freelancer).post(post).build()
        );

        try {
            freelancerTakePost.setStatus(TakePostStatus.valueOf(createTakePostDto.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.TAKE_POST_STATUS_INVALID);
        }
        String jobType = Objects.equals(post.getWork().getName(), JobType.HOUSECLEANING.toString()) ?"Dọn dẹp nhà" : "Trông trẻ";

        if(Objects.equals(createTakePostDto.getStatus(), TakePostStatus.ACCEPTED.toString())){
            post.setNumOfFreelancer(post.getNumOfFreelancer() + 1);
            if(post.getNumOfFreelancer() >= post.getTotalFreelancer()){
                post.setStatus(PostStatus.SCHEDULED);
            }
            String title = "Đã có freelancer nhận việc";
            String body = freelancer.getName() + " đã nhận công việc " + jobType + " mà bạn đăng";
            Notification notification = Notification.builder().title(title).content(body).post(post).build();
            UserNotification userNotification = UserNotification.builder().user(post.getCustomer()).notification(notification).build();
            userNotificationRepository.save(userNotification);
            firebaseUtil.sendNotification(post.getCustomer().getFirebaseToken(), title, body );
        }else if (Objects.equals(createTakePostDto.getStatus(), TakePostStatus.PENDING.toString())){
            String title = "Bạn có 1 yêu cầu công việc mới";
            String body = post.getCustomer().getName() + " đã gửi yêu cầu công việc " + jobType +" đến bạn";
            Notification notification = Notification.builder().title(title).content(body).post(post).build();
            UserNotification userNotification = UserNotification.builder().user(post.getCustomer()).notification(notification).build();
            userNotificationRepository.save(userNotification);
            firebaseUtil.sendNotification(freelancer.getFirebaseToken(), title, body );
        }

        freelancerTakePost = freelancerTakePostRepository.save(freelancerTakePost);


        return modelMapper.map(freelancerTakePost, TakePostDto.class);
    }

    public GetPostDto cancelPost(String id, CancelPostDto cancelPostDto){
        Post post = getPostById(id, true);

        if(cancelPostDto == null){
            User user = post.getCustomer();
            // add reputation login
            post.setStatus(PostStatus.CANCELED);
        }else{
            User freelancer = userService.getByIdAndRole(cancelPostDto.getFreelancerId(), UserRole.FREELANCER);
            // add reputation login
            FreelancerTakePost freelancerTakePost = freelancerTakePostRepository.findByFreelancerAndPost(freelancer, post).orElseThrow(
                    () -> new AppException(StatusCode.TAKE_POST_NOT_FOUND)
            );
            freelancerTakePostRepository.delete(freelancerTakePost);
        }

        return modelMapper.map(post, GetPostDto.class);
    }

    
    public List<GetPostDto> getAllPosts(int page, int size, String sortBy, String sortDirection, String freelancerId) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        List<Post> posts;

        if(freelancerId != null){
            User freelancer = userService.getByIdAndRole(freelancerId, UserRole.FREELANCER);
            userMiddleware.checkUserStatus(freelancer.getStatus());
            Address address = freelancer.getAddresses().stream().filter(Address::isDefault).findFirst().orElse(null);

            if(address != null){
                List<String> workIds = freelancer.getFreelancerWorkServices().stream()
                        .filter(work -> work.getStatus() == FreelancerWorkStatus.WORK)
                        .map(work -> work.getWork().getId())
                        .toList();
                posts = postRepository.findAllByWorkInAndStatusIsAndDeletedIsFalseAndSortByDistance(
                        freelancer.getId(),
                        TakePostStatus.ACCEPTED,
                        workIds,
                        PostStatus.INITIAL,
                        address.getLatitude(),
                        address.getLongitude(),
                        pageable
                ).getContent();
            }else{
                List<Work> works = freelancer.getFreelancerWorkServices().stream().filter(
                        work -> work.getStatus() == FreelancerWorkStatus.WORK
                ).map(
                        FreelancerWorkService::getWork
                ).toList();
                posts = postRepository.findAllByWorkInAndStatusIsAndDeletedIsFalse(works, PostStatus.INITIAL, pageable).getContent();
            }
        }else{
            posts = postRepository.findAllByDeletedIsFalse(pageable).getContent();
        }

        return posts.stream().map(
                post -> modelMapper.map(post, GetPostDto.class)
        ).toList();
    }
    
    public List<GetPostDto> getAllPostsByCustomerId(String userId, int page, int size, String sortBy, String sortDirection, String workId, String packageName){
        User user = userService.getByIdAndRole(userId, UserRole.CUSTOMER);

        List<Post> posts;
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        if(workId != null){
            posts = postRepository.findAllByCustomerIdAndWorkIdAndDeletedIsFalse(user.getId(), workId, pageable).getContent();
        }else if(packageName!=null){
            List<PackageName> packageNames = new ArrayList<>(List.of());
            List<PostStatus> postStatuses = List.of(PostStatus.INITIAL, PostStatus.SCHEDULED, PostStatus.DOING);
            if(packageName.equals(PackageName._1DAY.name())){
                packageNames.add(PackageName._1DAY);
            }else{
                for (PackageName p : PackageName.values()) {
                    if (!p.equals(PackageName._1DAY)) {
                        packageNames.add(p);
                    }
                }
            }
            posts = postRepository.findAllByCustomerIdAndPackageNameInAndStatusInAndDeletedIsFalse(user.getId(), packageNames, postStatuses, pageable).getContent();
        }
        else{
            posts = postRepository.findAllByCustomerIdAndDeletedIsFalse(user.getId(), pageable).getContent();
        }

        return posts.stream().map(post -> {
            GetPostDto getPostDto = modelMapper.map(post, GetPostDto.class);
            getPostDto.getWorkSchedules().sort(Comparator.comparing(GetWorkScheduleDto::getDate));
            return getPostDto;
        }).toList();
    }

    public List<TakePostDto> getAllPostsByFreelancerId(String userId, int page, int size, String sortBy, String sortDirection, String workId, String packageName, String workStatus){
        User user = userService.getByIdAndRole(userId, null);

        List<FreelancerTakePost> freelancerTakePosts;

        Pageable pageable = PageRequest.of(page, size);
        if(workId != null){
            freelancerTakePosts = freelancerTakePostRepository.findAllPostsByWorkId(user.getId(), workId, pageable).getContent();
        }else if(packageName!=null){
            List<PackageName> packageNames = new ArrayList<>(List.of());
            List<PostStatus> postStatuses = List.of(PostStatus.INITIAL, PostStatus.SCHEDULED, PostStatus.DOING);
            if(packageName.equals(PackageName._1DAY.name())){
                packageNames.add(PackageName._1DAY);
            }else{
                for (PackageName p : PackageName.values()) {
                    if (!p.equals(PackageName._1DAY)) {
                        packageNames.add(p);
                    }
                }
            }
            freelancerTakePosts = freelancerTakePostRepository.findAllPostsByPackageName(user.getId(), packageNames, TakePostStatus.ACCEPTED, postStatuses, pageable).getContent();
        }else if(workStatus!=null){
            freelancerTakePosts = freelancerTakePostRepository.findAllPostsByTakePostStatus(user.getId(), TakePostStatus.PENDING, PostStatus.INITIAL, pageable).getContent();
        }else{
            freelancerTakePosts = freelancerTakePostRepository.findAllPostsByDifferentStatus(user.getId(), TakePostStatus.PENDING , pageable).getContent();
        }

        return freelancerTakePosts.stream().map(freelancerTakePost -> {
            TakePostDto takePostDto = modelMapper.map(freelancerTakePost, TakePostDto.class);
            takePostDto.getPost().getWorkSchedules().sort(Comparator.comparing(GetWorkScheduleDto::getDate));
            return takePostDto;
        }).toList();
    }

    public GetPostDetailDto getById(String id){
        Post post = getPostById(id, true);
        GetPostDetailDto getPostDetailDto = modelMapper.map(post, GetPostDetailDto.class);
        getPostDetailDto.getWorkSchedules().sort(Comparator.comparing(GetWorkScheduleDto::getDate));
        return getPostDetailDto;
    }

    public List<FreelancerTakeDto> getFreelancerByPostsId(String id, int page, int size, String sortBy, String sortDirection, String takePostStatus){
        Post post = getPostById(id, true);
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size);

        List<FreelancerTakePost> freelancerTakePosts;

        if(takePostStatus != null){
            try {
                freelancerTakePosts = freelancerTakePostRepository.findAllByPostAndStatus(post, TakePostStatus.valueOf(takePostStatus) , pageable).getContent();
            } catch (IllegalArgumentException e) {
                throw new AppException(StatusCode.TAKE_POST_STATUS_INVALID);
            }
        }else{
            freelancerTakePosts = freelancerTakePostRepository.findAllByPost(post, pageable).getContent();
        }

        return freelancerTakePosts.stream().map(
                freelancerTakePost -> modelMapper.map(freelancerTakePost, FreelancerTakeDto.class)
        ).toList();
    }


    public GetPostDto createPost(CreatePostDto createPostDto){
        Post post = modelMapper.map(createPostDto, Post.class);
        post.setStatus(PostStatus.INITIAL);

        Work work = workRepository.findById(createPostDto.getWorkId()).orElseThrow(
                ()->new AppException(StatusCode.JOB_TYPE_NOT_FOUND)
        );

        User customer = userService.getByIdAndRole(createPostDto.getCustomerId(), UserRole.CUSTOMER);

        userMiddleware.checkUserStatus(customer.getStatus());

        //check balance of customer
        if(post.getPaymentType()==PaymentType.QR){
            userMiddleware.checkUserBalance(customer.getBalance(),post.getPrice());
        }

        Address address = customer.getAddresses().stream().filter(addr -> Objects.equals(addr.getId(), createPostDto.getAddressId())).findFirst().orElseThrow(
                ()->new AppException(StatusCode.ADDRESS_NOT_FOUND)
        );

        try {
            post.setPaymentType(PaymentType.valueOf(createPostDto.getPaymentType()));
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.PAYMENT_TYPE_INVALID);
        }

        try {
            post.setPackageName(PackageName.valueOf(createPostDto.getPackageName()));
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.PACKAGE_NAME_INVALID);
        }

        post.setCustomer(customer);
        post.setAddress(address);
        post.setWork(work);


        if(Objects.equals(post.getWork().getName(), JobType.HOUSECLEANING.toString())){
            if(createPostDto.getHouseCleaning()==null){
                throw new AppException(StatusCode.MISSING_HOUSE_CLEANING);
            }
            HouseCleaning houseCleaning = modelMapper.map(createPostDto.getHouseCleaning(), HouseCleaning.class);
            houseCleaning.setPost(post);
            post.setHouseCleaning(houseCleaning);
        }else if(Objects.equals(post.getWork().getName(), JobType.BABYSITTING.toString())){
            if(createPostDto.getBabysitting()==null){
                throw new AppException(StatusCode.MISSING_BABYSITTING);
            }
            Babysitting babysitting = modelMapper.map(createPostDto.getBabysitting(), Babysitting.class);
            Set<Baby> babies = createPostDto.getBabysitting().getBabies().stream().map(
                    babyDto -> {
                        Baby baby = modelMapper.map(babyDto, Baby.class);
                        baby.setBabysitting(babysitting);
                        return baby;
                    }
            ).collect(Collectors.toSet());
            babysitting.setBabies(babies);
            babysitting.setPost(post);
            post.setBabysitting(babysitting);
        }else{
            throw new AppException(StatusCode.JOB_TYPE_NOT_FOUND);
        }

        Set<WorkSchedule> workSchedules = new HashSet<>();

        createPostDto.getWorkSchedules().forEach(
                workScheduleDto -> {
                    WorkSchedule workSchedule = modelMapper.map(workScheduleDto, WorkSchedule.class);
                    workSchedule.setPost(post);
                    workSchedule.setStatus(WorkScheduleStatus.INITIAL);
                    workSchedules.add(workSchedule);
                }
        );

        post.setWorkSchedules(workSchedules);

        if(post.getPaymentType() == PaymentType.QR){
            customer.setBalance(customer.getBalance() - post.getPrice());
        }

        Post finalPost = postRepository.save(post);

        work.setPostPerMonth(work.getPostPerMonth()+1);

        workRepository.save(work);

        return modelMapper.map(finalPost, GetPostDto.class);
    }
}
