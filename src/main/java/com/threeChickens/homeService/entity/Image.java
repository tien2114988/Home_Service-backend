package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "image")
    private FreelancerWorkService freelancerWorkService;
}
