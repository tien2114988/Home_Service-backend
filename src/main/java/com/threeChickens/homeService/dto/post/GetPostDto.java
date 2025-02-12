package com.threeChickens.homeService.dto.post;

import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.babysitting.BabysittingDto;
import com.threeChickens.homeService.dto.houseCleaning.HouseCleaningDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.dto.work.GetWorkDto;
import com.threeChickens.homeService.dto.workSchedule.GetWorkScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Time startTime;
    private int duration;
    private long price;
    private String status;
    private String paymentType;
    private boolean isPayment;
    private int totalFreelancer;
    private int numOfFreelancer;
    private String packageName;
    private int totalWorkDay;
    private int numOfWorkedDay;
    private boolean chooseFreelancer;

    private GetUserDto customer;
    private List<GetWorkScheduleDto> workSchedules;
    private GetWorkScheduleDto workSchedule;
    private GetWorkDto work;
    private HouseCleaningDto houseCleaning;
    private BabysittingDto babysitting;
    private GetAddressDto address;
//    private Set<FreelancerTakeDto> freelancerTakePosts;
//    private Set<GetRateDto> rates;
}
