package com.threeChickens.homeService.dto.test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTestDto {
    private String id;

    private int testDuration;

    private int passedPoint;

    private int questionCount;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "work_id", referencedColumnName = "id")
//    private Work work;
//
//    @OneToMany(mappedBy="test")
//    private Set<TestResult> testResults;
//
}
