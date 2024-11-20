package com.threeChickens.homeService.dto.province;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceDto {
    private int code;
    private String name;
    private List<DistrictDto> districts;
}
