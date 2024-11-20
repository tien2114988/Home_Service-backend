package com.threeChickens.homeService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Province {
    @Id
    private int code;

    private String name;

    @OneToMany(mappedBy = "province")
    private Set<District> districts = new HashSet<>();;
}
