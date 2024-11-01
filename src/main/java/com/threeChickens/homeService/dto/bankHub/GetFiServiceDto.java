package com.threeChickens.homeService.dto.bankHub;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
public class GetFiServiceDto {
    private String requestId;
    private List<FiServiceDto> fiServices;
}
