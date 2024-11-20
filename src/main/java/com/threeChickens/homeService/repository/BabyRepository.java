package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Baby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabyRepository extends JpaRepository<Baby,String> {

}
