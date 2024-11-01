package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;

    @OneToMany(mappedBy="test")
    private Set<TestResult> testResults;

    @OneToMany(mappedBy="test")
    private Set<Question> questions;
}
