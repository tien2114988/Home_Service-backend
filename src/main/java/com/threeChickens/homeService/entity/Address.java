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

    private String street;

    private String ward;

    private String district;

    private String province;

    private float latitude;

    private float longitude;

    private boolean isDefault;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
