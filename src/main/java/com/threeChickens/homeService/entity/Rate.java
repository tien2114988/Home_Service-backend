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

    private String comment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", referencedColumnName = "id")
    private User freelancer;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private User customer;
}
