package com.threeChickens.homeService.dto.vietQr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LookUpDto {
    private String code;
    private String desc;
    private LookUpDataDto data;
}
