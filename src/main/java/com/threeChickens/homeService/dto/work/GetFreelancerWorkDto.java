package com.threeChickens.homeService.dto.work;

import com.threeChickens.homeService.dto.user.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFreelancerWorkDto {
    private String status;
    private String description;
    private GetUserDto freelancer;
    private GetWorkDto work;
}
