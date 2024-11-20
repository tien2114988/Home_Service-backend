package com.threeChickens.homeService.dto.address;

import com.threeChickens.homeService.key.WardKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressWardDto {
    private WardKey code;
    private String name;
    private AddressDistrictDto district;
}
