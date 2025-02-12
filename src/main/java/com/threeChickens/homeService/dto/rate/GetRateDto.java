package com.threeChickens.homeService.dto.rate;


import com.threeChickens.homeService.dto.user.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRateDto {
    private GetUserDto freelancer;
    private String comment;
    private int star;
}
