package com.threeChickens.homeService.entity;

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

    private String status;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", referencedColumnName = "id")
    private User freelancer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;
}
