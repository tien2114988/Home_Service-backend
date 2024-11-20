package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String customerName;

    private String phoneNumber;

    private String detail;

    private float latitude;

    private float longitude;

    private boolean isDefault;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "district_code", referencedColumnName = "district_code"),
            @JoinColumn(name = "province_code", referencedColumnName = "province_code"),
            @JoinColumn(name = "ward_code", referencedColumnName = "code")})
    private Ward ward;
}
