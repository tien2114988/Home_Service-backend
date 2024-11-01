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
public class Babysitting {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private int numOfBaby;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @OneToMany(mappedBy="babysitting")
    private Set<Baby> babies;
}
