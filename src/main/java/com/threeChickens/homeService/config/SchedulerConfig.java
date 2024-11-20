package com.threeChickens.homeService.config;


import com.threeChickens.homeService.service.BankService;
import com.threeChickens.homeService.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Configuration
public class SchedulerConfig {
    @Autowired
    private OtpService otpService;

    private final WebClient webClient;

    // Inject WebClient instance
    public SchedulerConfig(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://home-service-backend-v1.onrender.com").build();
    }

    @Scheduled(fixedRate = 3600000) // Chạy mỗi giờ
    public void cleanUpExpiredOtp() {
        otpService.cleanupOtp();
    }


    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void reloadWebsite() {
        webClient.get()
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(response ->
                        System.out.println("Reloaded at " + LocalDateTime.now() + ": Status Code " + response.getStatusCode()))
                .doOnError(error ->
                        System.err.println("Error reloading at " + LocalDateTime.now() + ": " + error.getMessage()))
                .subscribe();
    }
}
