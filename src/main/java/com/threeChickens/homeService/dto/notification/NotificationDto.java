package com.threeChickens.homeService.dto.notification;

import com.threeChickens.homeService.dto.post.GetPostDetailDto;
import com.threeChickens.homeService.dto.post.GetPostIdDto;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private String id;
    private LocalDateTime createdAt;
    private String title;
    private String content;
    private GetPostIdDto post;
}
