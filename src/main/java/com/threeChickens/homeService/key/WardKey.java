package com.threeChickens.homeService.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WardKey implements Serializable {
    @Column(name = "code")
    private int code;

    @Column(name = "district_code")
    private DistrictKey districtCode;
}
