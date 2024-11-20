package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Ward;
import com.threeChickens.homeService.key.WardKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WardRepository extends JpaRepository<Ward, WardKey> {
    Optional<Ward> findByCode(WardKey code);
}
