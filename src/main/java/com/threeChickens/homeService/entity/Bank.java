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
public class Bank {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String bin;

    private String logo;

    private String name;

    private String color;

    private String code;

    private String fiName;

    private String fiFullName;

    private String fiServiceId;

    private String type;

    @OneToMany(mappedBy="bank")
    private Set<BankAccount> bankAccounts = new HashSet<>();;
}
