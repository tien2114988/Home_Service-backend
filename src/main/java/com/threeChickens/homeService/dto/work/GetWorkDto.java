package com.threeChickens.homeService.dto.work;

import com.threeChickens.homeService.dto.test.GetTestDto;
import com.threeChickens.homeService.enums.FreelancerWorkStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkDto {
    private String id;

    private String name;

    private String image;

    private String description;

    private GetTestDto test;

    private int postPerMonth;

    private int numOfFreelancers;

    private int NumOfRequests;

    private FreelancerWorkStatus status;
}
