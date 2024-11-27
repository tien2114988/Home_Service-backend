package com.threeChickens.homeService.dto.post;

import com.threeChickens.homeService.dto.babysitting.BabysittingDto;
import com.threeChickens.homeService.dto.houseCleaning.HouseCleaningDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    private String customerNote;
    private Time startTime;
    private int duration;
    private long price;
    private String paymentType;
    private boolean isPayment;
    private int totalFreelancer;
    private String packageName;
    private int totalWorkDay;
    private boolean chooseFreelancer;

    private HouseCleaningDto houseCleaning;
    private BabysittingDto babysitting;
    private Set<CreateWorkScheduleDto> workSchedules;

    private String customerId;
    private String addressId;
    private String workId;
}
