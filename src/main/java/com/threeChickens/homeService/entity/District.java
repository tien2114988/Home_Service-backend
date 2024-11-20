package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.key.DistrictKey;
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
public class District {
    @EmbeddedId
    private DistrictKey code;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("provinceCode")
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    private Province province;

    @OneToMany(mappedBy = "district")
    private Set<Ward> wards = new HashSet<>();;
}
