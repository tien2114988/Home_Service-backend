package com.threeChickens.homeService.config;

import com.threeChickens.homeService.service.AdminService;
import com.threeChickens.homeService.service.BankService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AppConfig {
    @Autowired
    private AdminService adminService;

    @Autowired
    private BankService bankService;

    @Bean
    @Transactional
    ApplicationRunner applicationRunner(){
        return args -> {
            adminService.initAdminAccounts();
            bankService.initBanks();
        };
    }
}
