package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AnswerForQuestion {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_result_id", referencedColumnName = "id")
    private TestResult testResult;
}
