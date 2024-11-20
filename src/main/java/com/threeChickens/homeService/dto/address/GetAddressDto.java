package com.threeChickens.homeService.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressDto {
    private String id;
    private String customerName;
    private String phoneNumber;
    private String detail;
    private AddressWardDto ward;
    private float latitude;
    private float longitude;
    private boolean isDefault;
    private boolean deleted;
}
