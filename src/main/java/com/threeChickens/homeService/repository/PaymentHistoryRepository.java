package com.threeChickens.homeService.repository;

import com.threeChickens.homeService.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, String> {

}
