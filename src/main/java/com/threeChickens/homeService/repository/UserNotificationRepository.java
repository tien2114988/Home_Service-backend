package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, String> {
}
