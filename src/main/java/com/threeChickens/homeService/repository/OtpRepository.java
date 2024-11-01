package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    void deleteAllByEmail(String email);
    Optional<Otp> findFirstByEmailAndOtpOrderByCreatedDateDesc(String email, String otp);
    Optional<Otp> findFirstByEmailAndVerifiedIsTrueOrderByCreatedDateDesc(String email);
}
