package com.threeChickens.homeService.config;


import com.threeChickens.homeService.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
public class SchedulerConfig {
    @Autowired
    private OtpService otpService;

    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    public void cleanUpExpiredOtp() {
        otpService.cleanupOtp();
    }
}
