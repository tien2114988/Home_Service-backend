package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.HouseCleaning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseCleaningRepository extends JpaRepository<HouseCleaning, String> {
}
