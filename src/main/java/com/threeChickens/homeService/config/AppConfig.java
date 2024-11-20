package com.threeChickens.homeService.config;


import com.threeChickens.homeService.repository.UserRepository;
import com.threeChickens.homeService.service.*;
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

    @Autowired
    private WorkService workService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Bean
    @Transactional
    ApplicationRunner applicationRunner(){
        return args -> {
            adminService.initAdminAccounts();
            bankService.initBanks();
            workService.initWorks();
            userService.initUsers();
            addressService.initProvinces();
        };
    }

}
