package com.threeChickens.homeService.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTestDto {
    private int testDuration;

    private int passedPoint;

    private int questionCount;
}
