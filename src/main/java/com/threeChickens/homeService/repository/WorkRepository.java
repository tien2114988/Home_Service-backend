package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, String> {
}
