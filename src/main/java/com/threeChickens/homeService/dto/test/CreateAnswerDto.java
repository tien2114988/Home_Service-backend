package com.threeChickens.homeService.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnswerDto {
    private String questionId;
    private String choiceId;
    private String content;
}
