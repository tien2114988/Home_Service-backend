package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, String> {
}
