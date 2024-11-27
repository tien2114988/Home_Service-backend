package com.threeChickens.homeService.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetQuestionDto {
    private String id;

    private String type;

    private String content;

    private Set<ChoiceDto> choices;
}
