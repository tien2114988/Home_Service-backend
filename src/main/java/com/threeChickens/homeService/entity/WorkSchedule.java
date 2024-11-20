package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.WorkScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkSchedule {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private WorkScheduleStatus status;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;
}
