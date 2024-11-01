package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private Date date;

    private String status;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;
}
