package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String content;

    private boolean isAnswer;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="question_id", nullable=false)
    private Question question;
}
