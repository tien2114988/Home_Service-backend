package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.bankHub.CancelPostDto;
import com.threeChickens.homeService.dto.post.CreatePostDto;
import com.threeChickens.homeService.dto.post.GetPostDto;
import com.threeChickens.homeService.dto.post.CreateTakePostDto;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private HouseCleaningRepository houseCleaningRepository;

    @Autowired
    private BabysittingRepository babysittingRepository;

    @Autowired
    private WorkScheduleRepository workScheduleRepository;

    @Autowired
    private FreelancerTakePostRepository freelancerTakePostRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public Post getPostById(String id, boolean isCheck){
        if(isCheck){
            return postRepository.findByIdAndDeletedIsFalse(id).orElseThrow(
                    () -> new AppException(StatusCode.POST_NOT_FOUND)
            );
        }else{
            return postRepository.findByIdAndDeletedIsFalse(id).orElse(null);
        }
    }

    public GetPostDto takePost(String id, CreateTakePostDto createTakePostDto){
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

        if(Objects.equals(createTakePostDto.getStatus(), TakePostStatus.ACCEPTED.toString())){
            post.setNumOfFreelancer(post.getNumOfFreelancer() + 1);
            if(post.getNumOfFreelancer() >= post.getTotalFreelancer()){
                post.setStatus(PostStatus.SCHEDULED);
            }
        }

        freelancerTakePostRepository.save(freelancerTakePost);

        post.getFreelancerTakePosts().add(freelancerTakePost);

        return modelMapper.map(post, GetPostDto.class);
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

    
    public List<GetPostDto> getAllPosts() {
        List<Post> posts = postRepository.findAllByDeletedIsFalse();
        
        return posts.stream().map(
                post -> modelMapper.map(post, GetPostDto.class)
        ).toList();
    }
    
    public List<GetPostDto> getAllPostsByUserId(String userId){
        User user = userService.getByIdAndRole(userId, null);

        List<Post> posts = List.of();
        
        if(user.getRole() == UserRole.CUSTOMER){
            posts = postRepository.findAllByCustomerIdAndDeletedIsFalse(user.getId());
        }else if(user.getRole() == UserRole.FREELANCER){
            posts = user.getFreelancerTakePosts().stream().filter(freelancerTakePost -> freelancerTakePost.getStatus() == TakePostStatus.ACCEPTED).map(
                    FreelancerTakePost::getPost
            ).toList();
        }
        
        return posts.stream().map(
                post -> modelMapper.map(post, GetPostDto.class)
        ).toList();
    }

    public GetPostDto createPost(CreatePostDto createPostDto){
        Post post = modelMapper.map(createPostDto, Post.class);
        post.setStatus(PostStatus.INITIAL);

        Work work = workRepository.findById(createPostDto.getWorkId()).orElseThrow(
                ()->new AppException(StatusCode.JOB_TYPE_NOT_FOUND)
        );

        User customer = userService.getByIdAndRole(createPostDto.getCustomerId(), UserRole.CUSTOMER);
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

        Post finalPost = postRepository.save(post);

        return modelMapper.map(finalPost, GetPostDto.class);
    }
}
