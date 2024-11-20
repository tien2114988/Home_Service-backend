package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.FreelancerWorkStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FreelancerWorkService {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private FreelancerWorkStatus status;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", nullable=false)
    private User freelancer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_id", nullable=false)
    private Work work;
}
