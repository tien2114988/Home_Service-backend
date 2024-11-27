package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.work.CreateFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.CreateWorkDto;
import com.threeChickens.homeService.dto.work.GetFreelancerWorkDto;
import com.threeChickens.homeService.dto.work.GetWorkDto;
import com.threeChickens.homeService.entity.*;
import com.threeChickens.homeService.enums.FreelancerWorkStatus;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.FreelancerWorkRepository;
import com.threeChickens.homeService.repository.WorkRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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

    public void initWorks(){
        if(workRepository.count()==0){
            Work work1 = Work.builder().name("HOUSECLEANING")
                    .image("https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/169183/Originals/fba1a1bb-1-1.jpg")
                    .description("Công việc dọn dẹp nhà bao gồm các nhiệm vụ cơ bản để duy trì sự sạch sẽ và gọn gàng của không gian sống. Nhân viên sẽ chịu trách nhiệm lau chùi sàn nhà, vệ sinh nội thất, làm sạch cửa sổ, dọn dẹp phòng tắm và nhà bếp, cùng việc sắp xếp lại đồ đạc nếu cần. Dịch vụ này đặc biệt hữu ích cho các gia đình bận rộn hoặc những người không có thời gian để tự mình dọn dẹp. Sự chuyên nghiệp, chu đáo và tinh thần trách nhiệm là yếu tố quan trọng để đảm bảo khách hàng luôn hài lòng.").build();
            Work work2 = Work.builder().name("BABYSITTING")
                    .image("https://www.droppii.com/wp-content/uploads/2023/01/Phai-that-su-yeu-tre-con.jpg")
                    .description("Công việc trông trẻ tập trung vào việc chăm sóc và bảo vệ trẻ em trong khi cha mẹ vắng mặt. Nhân viên trông trẻ sẽ đảm bảo an toàn cho trẻ, hỗ trợ các hoạt động học tập và vui chơi, chuẩn bị bữa ăn nhẹ và giúp trẻ tuân thủ lịch sinh hoạt hàng ngày. Dịch vụ này yêu cầu sự tận tâm, kỹ năng giao tiếp tốt, và khả năng xử lý tình huống linh hoạt để mang lại sự yên tâm tuyệt đối cho phụ huynh.").build();
            workRepository.save(work1);
            workRepository.save(work2);
        }
    }

    public List<GetWorkDto> getAllWorks(){
        List<Work> works = workRepository.findAll();
        return works.stream().map(
                work -> modelMapper.map(work, GetWorkDto.class)
        ).toList();
    }

    public Work getWorkById(String id){
        return workRepository.findById(id).orElseThrow(
                ()-> new AppException(StatusCode.JOB_TYPE_NOT_FOUND)
        );
    }


    public GetFreelancerWorkDto provideService(String workId, String freelancerId, CreateFreelancerWorkDto createFreelancerWorkDto){
        Work work = getWorkById(workId);

        User freelancer = userService.getByIdAndRole(freelancerId, UserRole.FREELANCER);

        FreelancerWorkService freelancerWorkService =  freelancerWorkRepository.findByWorkIdAndFreelancerId(workId, freelancerId).orElse(
                null
        );

        try {
            if(freelancerWorkService!=null){
                modelMapper.map(createFreelancerWorkDto, freelancerWorkService);
            }else{
                freelancerWorkService = FreelancerWorkService.builder()
                        .work(work)
                        .freelancer(freelancer)
                        .status(FreelancerWorkStatus.valueOf(createFreelancerWorkDto.getStatus()))
                        .description(createFreelancerWorkDto.getDescription())
                        .build();
            }
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.FREELANCER_WORK_STATUS_INVALID);
        }

        freelancerWorkService = freelancerWorkRepository.save(freelancerWorkService);

        return modelMapper.map(freelancerWorkService, GetFreelancerWorkDto.class);
    }

    public List<GetFreelancerWorkDto> getAllFreelancersByWork(String id, String postId){
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
                freelancerWorkService -> modelMapper.map(freelancerWorkService, GetFreelancerWorkDto.class)
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
