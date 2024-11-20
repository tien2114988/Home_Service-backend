package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, String> {
}
