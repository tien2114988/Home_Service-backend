package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {
    @Query("SELECT DISTINCT b.fiName, b.bin, b.logo FROM Bank b")
    List<Object[]> findDistinctFiNameAndFiBin();

    Optional<Bank> findFirstByBin(String bin);
}
