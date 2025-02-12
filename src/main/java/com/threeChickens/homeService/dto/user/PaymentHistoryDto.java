package com.threeChickens.homeService.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDto {
    private String id;
    private String refId;
    private int amount;
    private LocalDateTime createdAt;
}
