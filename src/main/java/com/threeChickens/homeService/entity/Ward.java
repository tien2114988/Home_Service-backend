package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.key.DistrictKey;
import com.threeChickens.homeService.key.WardKey;
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
public class Ward {
    @EmbeddedId
    private WardKey code;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("districtCode")
    @JoinColumns({@JoinColumn(name = "district_code", referencedColumnName = "code"),
            @JoinColumn(name = "province_code", referencedColumnName = "province_code")})
    private District district;

    @OneToMany(mappedBy="ward")
    private Set<Address> addresses = new HashSet<>();;
}
