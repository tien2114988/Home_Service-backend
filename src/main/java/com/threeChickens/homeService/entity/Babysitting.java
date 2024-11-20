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
public class Babysitting {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private int numOfBaby;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    @OneToMany(mappedBy="babysitting", cascade = CascadeType.ALL)
    private Set<Baby> babies = new HashSet<>();;
}
