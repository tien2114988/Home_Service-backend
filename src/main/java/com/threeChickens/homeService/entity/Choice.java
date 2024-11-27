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
public class Choice {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean isAnswer;

    private boolean deleted;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    @OneToMany(mappedBy = "choice")
    private Set<AnswerForQuestion> answerForQuestions = new HashSet<>();
}
