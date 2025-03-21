package com.threeChickens.homeService.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedisNotificationDto implements Serializable {
    private int id;
    private String title;
    private boolean isView;
    private String content;
    private String postId;
    private LocalDateTime createdAt;
}
