package com.threeChickens.homeService.dto.test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.threeChickens.homeService.entity.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateChoiceDto {
    private String content;
    private boolean isAnswer;
}
