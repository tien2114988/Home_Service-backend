package com.threeChickens.homeService.dto.address;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressDto {
//    private Integer provinceCode;
//    private Integer districtCode;
//    private Integer wardCode;
    @NotNull(message = "Customer name must not be null")
    private String customerName;

    @NotNull(message = "Phone number must not be null")
    private String phoneNumber;

    @NotNull(message = "Address detail must not be null")
    private String detail;

    @NotNull(message = "Place id must not be null")
    private String placeId;

    @NotNull(message = "Latitude must not be null")
    private float latitude;

    @NotNull(message = "Longitude must not be null")
    private float longitude;

    private boolean isDefault;
}
