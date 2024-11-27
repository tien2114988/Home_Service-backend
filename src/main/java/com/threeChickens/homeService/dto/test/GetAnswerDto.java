package com.threeChickens.homeService.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAnswerDto {
    private GetQuestionDto question;
    private ChoiceDto choice;
    private String content;
    private boolean correct;
}
