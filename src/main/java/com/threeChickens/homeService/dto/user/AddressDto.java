package com.threeChickens.homeService.dto.user;

import com.threeChickens.homeService.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String id;
    private String customerName;
    private String phoneNumber;
    private String street;
    private String ward;
    private String district;
    private String province;
    private float latitude;
    private float longitude;
    private boolean isDefault;
}
