package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.work.CreateFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.CreateWorkDto;
import com.threeChickens.homeService.dto.work.GetDetailFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.GetWorkDto;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.*;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.FreelancerWorkRepository;
import com.threeChickens.homeService.repository.WorkRepository;
import com.threeChickens.homeService.utils.FileUploadUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class WorkService {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private FreelancerWorkRepository freelancerWorkRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    public List<GetWorkDto> getAllWorks(String freelancerId){
        List<Work> works = workRepository.findAll();
        return works.stream().map(
                work -> {
                    GetWorkDto getWorkDto = modelMapper.map(work, GetWorkDto.class);
                    int freelancerCount = work.getFreelancerWorkServices().stream().filter(req->req.getStatus()==FreelancerWorkStatus.WORK && !req.getFreelancer().isDeleted()).toList().size();
                    int requestCount = work.getFreelancerWorkServices().stream().filter(req->req.getStatus()==FreelancerWorkStatus.INITIAL && !req.getFreelancer().isDeleted()).toList().size();
                    getWorkDto.setNumOfFreelancers(freelancerCount);
                    getWorkDto.setNumOfRequests(requestCount);
                    if(freelancerId != null){
                        User freelancer = userService.getByIdAndRole(freelancerId, UserRole.FREELANCER);
                        Optional<FreelancerWorkService> freelancerWork = freelancer.getFreelancerWorkServices().stream().filter(freelancerWorkService -> Objects.equals(freelancerWorkService.getWork().getId(), work.getId())).findFirst();
                        if(freelancerWork.isEmpty()){
                            getWorkDto.setStatus(null);
                        }else{
                            getWorkDto.setStatus(freelancerWork.get().getStatus());
                        }
                    }
                    return getWorkDto;
                }
        ).toList();
    }

    public Work getWorkById(String id){
        return workRepository.findById(id).orElseThrow(
                ()-> new AppException(StatusCode.JOB_TYPE_NOT_FOUND)
        );
    }

    public GetDetailFreelancerWorkDto uploadImages(String id, MultipartFile[] images){
        FreelancerWorkService freelancerWorkService = freelancerWorkRepository.findById(id).orElseThrow(
                () -> new AppException(StatusCode.FREELANCER_WORK_NOT_FOUND)
        );

        Set<Image> freelancerWorkImages = new HashSet<>();

        AtomicInteger i = new AtomicInteger();
        Arrays.stream(images).forEach((image) -> {
            String filePath = fileUploadUtil.saveImage(image,  id + "_" + i.getAndIncrement());
            Image freelancerWorkImage = Image.builder().link(filePath).freelancerWorkService(freelancerWorkService).build();
            freelancerWorkImages.add(freelancerWorkImage);
        });
        freelancerWorkService.setImages(freelancerWorkImages);

        FreelancerWorkService finalFreelancerWorkService = freelancerWorkRepository.save(freelancerWorkService);
        return modelMapper.map(finalFreelancerWorkService, GetDetailFreelancerWorkDto.class);
    }


    public GetDetailFreelancerWorkDto provideService(String workId, String freelancerId, CreateFreelancerWorkDto createFreelancerWorkDto){
        Work work = getWorkById(workId);
        User freelancer = userService.getByIdAndRole(freelancerId, UserRole.FREELANCER);

        TestResult testResult = null;

        if(Objects.equals(createFreelancerWorkDto.getStatus(), FreelancerWorkStatus.INITIAL.toString())){
            testResult = testService.getTestResultById(createFreelancerWorkDto.getTestResultId());

        }


        FreelancerWorkService freelancerWorkService =  freelancerWorkRepository.findByWorkIdAndFreelancerId(workId, freelancerId).orElse(
                null
        );

        try {
            if(freelancerWorkService!=null){
                modelMapper.map(createFreelancerWorkDto, freelancerWorkService);
                freelancerWorkService.setTestResult(testResult);
            }else{
                freelancerWorkService = FreelancerWorkService.builder()
                        .work(work)
                        .freelancer(freelancer)
                        .testResult(testResult)
                        .status(FreelancerWorkStatus.valueOf(createFreelancerWorkDto.getStatus()))
                        .description(createFreelancerWorkDto.getDescription())
                        .build();
            }
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.FREELANCER_WORK_STATUS_INVALID);
        }

        freelancerWorkService = freelancerWorkRepository.save(freelancerWorkService);

        return modelMapper.map(freelancerWorkService, GetDetailFreelancerWorkDto.class);
    }

    public List<GetDetailFreelancerWorkDto> getAllFreelancersByWork(String id, String postId){
        List<FreelancerWorkService> freelancerWorkServices;

        if(id == null){
            List<Work> works = workRepository.findAll();
            freelancerWorkServices = works.stream()
                    .flatMap(work -> work.getFreelancerWorkServices().stream()) // Flatten the list of freelancer work services
                    .collect(Collectors.toList());
        }else{
            Work work = getWorkById(id);
            freelancerWorkServices = work.getFreelancerWorkServices().stream().toList();
        }

        if(postId!=null){
            Post post = postService.getPostById(postId, false);

            if(post!=null){
                freelancerWorkServices = freelancerWorkServices.stream().filter(
                        freelancerWorkService -> {
                            return freelancerWorkService.getStatus() == FreelancerWorkStatus.WORK;
                        }
                ).toList();
            }
        }else{
            freelancerWorkServices = freelancerWorkServices.stream()
                    .sorted(Comparator.comparing(freelancerWorkService ->
                            freelancerWorkService.getStatus() == FreelancerWorkStatus.INITIAL ? 0 : 1))
                    .collect(Collectors.toList());
        }
        return freelancerWorkServices.stream().map(
                freelancerWorkService -> modelMapper.map(freelancerWorkService, GetDetailFreelancerWorkDto.class)
        ).toList();
    }

    public GetWorkDto updateWork(String id, CreateWorkDto createWorkDto){
        Work work = getWorkById(id);
        modelMapper.map(createWorkDto, work);
        if(createWorkDto.getCreateTestDto()!=null){
            if(work.getTest()!=null){
                modelMapper.map(createWorkDto.getCreateTestDto(), work.getTest());
            }else{
                Test test = modelMapper.map(createWorkDto.getCreateTestDto(), Test.class);
                work.setTest(test);
                test.setWork(work);
            }

        }

        work = workRepository.save(work);
        return modelMapper.map(work, GetWorkDto.class);
    }
}
