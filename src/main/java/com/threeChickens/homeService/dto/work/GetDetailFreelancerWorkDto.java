package com.threeChickens.homeService.dto.work;

import com.threeChickens.homeService.dto.rate.GetRateDto;
import com.threeChickens.homeService.dto.test.GetTestResultDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDetailFreelancerWorkDto {
    private String id;
    private String status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GetUserDto freelancer;
    private GetWorkDto work;
    private GetTestResultDto testResult;
    private Set<GetRateDto> rates;
}
