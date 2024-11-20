package com.threeChickens.homeService.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {
    private String status;
    private CreateTakePostDto createTakePostDto;
}
