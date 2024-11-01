package com.threeChickens.homeService.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {
    private String id;
    private String name;
    private String logo;
    private String code;
    private String bin;
    private String fiServiceId;
    private String fiName;
    private String fiFullName;
    private String color;
}
