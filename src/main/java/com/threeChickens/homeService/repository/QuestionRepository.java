package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    Optional<Question> findByIdAndDeletedIsFalse(String id);
}
