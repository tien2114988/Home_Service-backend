package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.AnswerForQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerForQuestion, String> {
}
