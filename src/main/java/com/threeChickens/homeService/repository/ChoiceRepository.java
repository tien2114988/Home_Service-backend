package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, String> {
    Optional<Choice> findByIdAndDeletedIsFalse(String id);
}
