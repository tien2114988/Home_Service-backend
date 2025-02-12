package com.threeChickens.homeService.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNotificationDto {
    private String id;
    private boolean isView;
    private NotificationDto notification;
}
