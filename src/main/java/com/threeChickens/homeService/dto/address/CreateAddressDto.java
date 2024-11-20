package com.threeChickens.homeService.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDto {
    private Integer provinceCode;
    private Integer districtCode;
    private Integer wardCode;
    private String customerName;
    private String phoneNumber;
    private String detail;
    private float latitude;
    private float longitude;
    private boolean isDefault;
}
