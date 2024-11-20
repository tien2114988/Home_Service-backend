package com.threeChickens.homeService.dto.address;

import com.threeChickens.homeService.key.DistrictKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDistrictDto {
    private DistrictKey code;
    private String name;
    private AddressProvinceDto province;
}
