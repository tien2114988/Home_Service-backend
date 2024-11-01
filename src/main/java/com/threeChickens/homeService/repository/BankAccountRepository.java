package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
