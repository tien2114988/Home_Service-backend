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
public class Image {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String link;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_work_service_id")
    private FreelancerWorkService freelancerWorkService;
}
