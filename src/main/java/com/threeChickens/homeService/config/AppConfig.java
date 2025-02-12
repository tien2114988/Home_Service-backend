package com.threeChickens.homeService.config;


import com.threeChickens.homeService.repository.UserRepository;
import com.threeChickens.homeService.service.*;
import com.threeChickens.homeService.utils.SeedDataUtil;
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
    private SeedDataUtil seedDataUtil;

    @Bean
    @Transactional
    ApplicationRunner applicationRunner(){
        return args -> {
            seedDataUtil.seedData();
        };
    }

}
