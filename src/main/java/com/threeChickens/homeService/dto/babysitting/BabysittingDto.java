package com.threeChickens.homeService.dto.babysitting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BabysittingDto {
    private int numOfBaby;
    private Set<BabyDto> babies;
}
