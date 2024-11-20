package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String type;

    private String content;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="test_id", nullable=false)
    private Test test;

    @OneToMany(mappedBy = "question")
    private Set<AnswerForQuestion> answerForQuestions = new HashSet<>();;

    @OneToMany(mappedBy="question")
    private Set<Choice> choices = new HashSet<>();;
}
