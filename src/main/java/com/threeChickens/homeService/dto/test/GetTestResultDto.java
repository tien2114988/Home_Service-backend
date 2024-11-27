package com.threeChickens.homeService.dto.test;

import com.threeChickens.homeService.dto.user.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTestResultDto {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int point;
    private boolean isPassed;
    private int numOfCorrectAnswers;
    private GetUserDto freelancer;
    private Set<GetAnswerDto> answerForQuestions;
}
