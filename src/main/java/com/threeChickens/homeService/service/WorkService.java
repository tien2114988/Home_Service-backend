package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.work.FreelancerDto;
import com.threeChickens.homeService.dto.work.FreelancerWorkDto;
import com.threeChickens.homeService.dto.work.CreateWorkDto;
import com.threeChickens.homeService.entity.FreelancerWorkService;
import com.threeChickens.homeService.entity.Post;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.entity.Work;
import com.threeChickens.homeService.enums.FreelancerWorkStatus;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.FreelancerWorkRepository;
import com.threeChickens.homeService.repository.WorkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void initWorks(){
        if(workRepository.count()==0){
            Work work1 = Work.builder().name("HOUSECLEANING").image("").description("").build();
            Work work2 = Work.builder().name("BABYSITTING").image("").description("").build();
            workRepository.save(work1);
            workRepository.save(work2);
        }
    }

    public Work getWorkById(String id){
        return workRepository.findById(id).orElseThrow(
                ()-> new AppException(StatusCode.JOB_TYPE_NOT_FOUND)
        );
    }

    public void provideService(String workId, String freelancerId, FreelancerWorkDto freelancerWorkDto){
        Work work = getWorkById(workId);

        User freelancer = userService.getByIdAndRole(freelancerId, UserRole.FREELANCER);

        try {
            FreelancerWorkService freelancerWorkService =  freelancerWorkRepository.findByWorkIdAndFreelancerId(workId, freelancerId).orElse(
                    FreelancerWorkService.builder().work(work).freelancer(freelancer).status(FreelancerWorkStatus.valueOf(freelancerWorkDto.getStatus())).build()
            );
            freelancerWorkRepository.save(freelancerWorkService);
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.ROLE_INVALID);
        }
    }

    public List<FreelancerDto> getAllFreelancersWithEmptyCalendar(String id, String postId){
        Work work = getWorkById(id);
        Post post = postService.getPostById(postId);
        return work.getFreelancerWorkServices().stream().filter(
                freelancerWorkService -> {

                    return freelancerWorkService.getStatus() == FreelancerWorkStatus.WORK;
                }
        ).map(
                freelancerWorkService-> modelMapper.map(freelancerWorkService.getFreelancer(), FreelancerDto.class)
        ).toList();
    }

    public CreateWorkDto updateWork(String id, CreateWorkDto createWorkDto){
        Work work = getWorkById(id);

        testService.createTest(work, createWorkDto.getCreateTestDto());

        return createWorkDto;
    }


}
