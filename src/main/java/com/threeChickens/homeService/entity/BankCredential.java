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
public class BankCredential {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String accessToken;

    private String grantId;

    private String ownerName;

    private boolean deleted;

    @OneToMany(mappedBy="bankCredential")
    private Set<BankAccount> bankAccounts = new HashSet<>();;
}

