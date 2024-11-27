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
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private int testDuration;

    private int passedPoint;

    private int questionCount;

    private boolean deleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_id", referencedColumnName = "id")
    private Work work;

    @OneToMany(mappedBy="test")
    private Set<TestResult> testResults = new HashSet<>();

    @OneToMany(mappedBy="test")
    private Set<Question> questions = new HashSet<>();
}
