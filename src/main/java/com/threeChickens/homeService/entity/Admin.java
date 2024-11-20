package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String userName;

    private String name;

    private String password;
}
