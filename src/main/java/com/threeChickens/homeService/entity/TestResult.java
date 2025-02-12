package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean isPassed;

    private int numOfCorrectAnswers;

    private int point;

    private boolean deleted;

    @OneToOne(mappedBy = "testResult", cascade = CascadeType.ALL)
    private FreelancerWorkService freelancerWorkService;

    @ManyToOne
    @JoinColumn(name="test_id", nullable=false)
    private Test test;

    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL)
    private Set<AnswerForQuestion> answerForQuestions = new HashSet<>();
}
