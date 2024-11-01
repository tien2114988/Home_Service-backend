package com.threeChickens.homeService.dto.bankHub;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
public class FiServiceDto {
    private String uuid;
    private String id;
    private String code;
    private String name;
    private String logo;
    private String fiBin;
    private String type;
    private String fiName;
    private String fiFullName;
}
