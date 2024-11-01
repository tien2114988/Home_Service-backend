package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String accountNumber;

    private String accountName;

    private boolean isAdmin;

    private boolean isDefault;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bank_id")
    private Bank bank;

    @ManyToOne
    @JoinColumn(name="bank_credential_id")
    private BankCredential bankCredential;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
