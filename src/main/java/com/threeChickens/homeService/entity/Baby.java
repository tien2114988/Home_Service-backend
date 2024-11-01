package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Baby {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private int age;

    @ManyToOne
    @JoinColumn(name="babysitting_id", nullable=false)
    private Babysitting babysitting;
}
