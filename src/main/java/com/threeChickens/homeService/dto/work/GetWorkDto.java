package com.threeChickens.homeService.dto.work;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkDto {
    private String id;

    private String name;

    private String image;

    private String description;
}
