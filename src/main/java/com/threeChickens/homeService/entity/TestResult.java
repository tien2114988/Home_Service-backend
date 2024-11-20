package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "testResult")
    private Set<AnswerForQuestion> answerForQuestions = new HashSet<>();;
}
