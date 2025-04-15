package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Post;
import com.threeChickens.homeService.entity.WorkSchedule;
import com.threeChickens.homeService.enums.PostStatus;
import com.threeChickens.homeService.enums.WorkScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, String> {
    List<WorkSchedule> findAllByStatusInAndDeletedIsFalse(List<WorkScheduleStatus> postStatuses);
}
