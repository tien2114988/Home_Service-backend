package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Babysitting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabysittingRepository extends JpaRepository<Babysitting, String> {
}
