package com.threeChickens.homeService.dto.bankHub;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelPostDto {
    private String freelancerId;
}
