package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.WorkScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    private Time startTime;

    private Time endTime;

    @Enumerated(EnumType.STRING)
    private WorkScheduleStatus status;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    @OneToMany(mappedBy = "workSchedule", cascade = CascadeType.ALL)
    private Set<WorkScheduleImage> images = new HashSet<>();
}
