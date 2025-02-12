package com.threeChickens.homeService.dto.rate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateRateDto {
    private String freelancerId;
    private String comment;
    private int star;
}
