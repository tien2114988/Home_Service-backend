package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.QuestionType;
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

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="test_id", nullable=false)
    private Test test;

    @OneToMany(mappedBy = "question")
    private Set<AnswerForQuestion> answerForQuestions = new HashSet<>();

    @OneToMany(mappedBy="question", cascade=CascadeType.ALL)
    private Set<Choice> choices = new HashSet<>();
}
