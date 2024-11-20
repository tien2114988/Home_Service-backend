package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.District;
import com.threeChickens.homeService.entity.Province;
import com.threeChickens.homeService.key.DistrictKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<District, DistrictKey> {
    List<District> findByProvince(Province province);
}
