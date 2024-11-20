package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByIdAndDeletedIsFalse(String id);
}
