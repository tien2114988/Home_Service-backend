package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private int star;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable=false)
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_work_service_id", nullable=false)
    private FreelancerWorkService freelancerWorkService;

//    @ManyToOne
//    @JoinColumn(name="customer_id", nullable=false)
//    private User customer;
}
