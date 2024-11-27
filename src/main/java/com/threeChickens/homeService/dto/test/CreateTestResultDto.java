package com.threeChickens.homeService.dto.test;

import com.threeChickens.homeService.entity.AnswerForQuestion;
import com.threeChickens.homeService.entity.Test;
import com.threeChickens.homeService.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestResultDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String freelancerId;

    private Set<CreateAnswerDto> answerForQuestions;
}
