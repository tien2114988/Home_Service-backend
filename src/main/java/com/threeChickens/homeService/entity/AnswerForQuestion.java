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

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean correct;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable=false)
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_result_id", nullable=false)
    private TestResult testResult;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "choice_id")
    private Choice choice;
}
