package com.threeChickens.homeService.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceDto {
    private String id;

    private String content;

    private boolean isAnswer;

    private boolean deleted;
}
