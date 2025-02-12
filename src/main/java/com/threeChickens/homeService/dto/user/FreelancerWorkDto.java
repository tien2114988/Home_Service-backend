package com.threeChickens.homeService.dto.work;

import com.threeChickens.homeService.dto.test.GetTestResultDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerWorkDto {
    private String id;
    private String status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GetWorkDto work;
//    private GetTestResultDto testResult;
}
