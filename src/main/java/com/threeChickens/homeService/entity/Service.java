package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String name;

    private String image;

    private String description;

    @OneToOne(mappedBy = "service")
    private Test test;

    @OneToMany(mappedBy="service")
    private Set<Post> posts;

    @OneToOne(mappedBy = "service")
    private FreelancerWorkService freelancerWorkService;
}

