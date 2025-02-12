package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkScheduleImage {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String link;

    private boolean isStart;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_schedule_id", nullable=false)
    private WorkSchedule workSchedule;
}
