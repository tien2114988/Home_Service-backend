package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.FreelancerWorkService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreelancerWorkRepository extends JpaRepository<FreelancerWorkService, String> {
    Optional<FreelancerWorkService> findByWorkIdAndFreelancerId(String workId, String freelancerId);
}
