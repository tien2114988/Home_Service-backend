package com.threeChickens.homeService.dto.takePost;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.enums.TakePostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerTakeDto {
    private String id;
    private TakePostStatus status;
    private GetUserDto freelancer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
