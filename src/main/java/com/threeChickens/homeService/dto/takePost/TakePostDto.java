package com.threeChickens.homeService.dto.takePost;

import com.threeChickens.homeService.dto.post.GetPostDto;
import com.threeChickens.homeService.enums.TakePostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakePostDto {
    private String id;
    private TakePostStatus status;
    private GetPostDto post;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
