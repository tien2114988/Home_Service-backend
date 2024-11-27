package com.threeChickens.homeService.dto.post;

import com.threeChickens.homeService.enums.WorkScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkScheduleDto {
    private String id;
    private LocalDate date;
    private WorkScheduleStatus status;
}
