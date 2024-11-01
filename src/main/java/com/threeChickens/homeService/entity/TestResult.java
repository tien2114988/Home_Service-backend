package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestResult {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private boolean isPassed;

    private int numOfCorrectAnswers;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="freelancer_id", nullable=false)
    private User freelancer;

    @ManyToOne
    @JoinColumn(name="test_id", nullable=false)
    private Test test;

    @OneToOne(mappedBy = "testResult")
    private AnswerForQuestion answerForQuestion;
}
