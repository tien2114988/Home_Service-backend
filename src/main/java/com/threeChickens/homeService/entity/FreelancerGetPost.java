package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FreelancerGetPost {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", referencedColumnName = "id")
    private User freelancer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;
}
