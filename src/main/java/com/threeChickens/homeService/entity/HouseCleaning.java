package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HouseCleaning {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private float area;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;
}
