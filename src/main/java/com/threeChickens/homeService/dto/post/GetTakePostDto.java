package com.threeChickens.homeService.dto.post;

import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.enums.TakePostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTakePostDto {
    private TakePostStatus status;
    private GetUserDto freelancer;
}
