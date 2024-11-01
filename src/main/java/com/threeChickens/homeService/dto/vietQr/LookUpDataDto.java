package com.threeChickens.homeService.dto.vietQr;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookUpDataDto {
    private String id;
    private String name;
    private String internationalName;
    private String shortName;
    private String address;
    private String accountName;
}
