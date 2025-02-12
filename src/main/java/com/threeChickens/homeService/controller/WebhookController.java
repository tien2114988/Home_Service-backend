package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.payment.PayOsWebhookDto;
import com.threeChickens.homeService.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Webhook", description = "For webhook")
@RequestMapping("webhook")
public class WebhookController {
    @Autowired
    private UserService userService;

    @PostMapping("/payOs")
    public ResponseEntity<?> handlePayment(@RequestBody PayOsWebhookDto payOsWebhookDto) throws Exception {
        if (payOsWebhookDto.getData().getDescription().equals("VQRIO123") && payOsWebhookDto.getData().getOrderCode() == 123) {
            return ResponseEntity.ok().build();
        }
        if(payOsWebhookDto.isSuccess()){
            Long orderCode = payOsWebhookDto.getData().getOrderCode();
            System.out.println("orderCode : "+ orderCode);
            userService.handleRecharge(payOsWebhookDto.getData());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
