package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String avatar;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Date dob;

    private String name;

    private char gender;

    private String status;

    private long balance;

    private String phoneNumber;

    private int reputationPoint;

    private boolean deleted;

    @OneToMany(mappedBy="user")
    private Set<Address> addresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BankAccount bankAccount;

    @OneToMany(mappedBy="customer")
    private Set<Post> posts;

    @OneToMany(mappedBy="freelancer")
    private Set<TestResult> testResults;

    @ManyToMany
    private Set<User> notifications;

    @OneToOne(mappedBy = "freelancer")
    private FreelancerGetPost freelancerGetPost;

    @OneToOne(mappedBy = "freelancer")
    private FreelancerWorkService freelancerWorkService;

    @OneToOne(mappedBy = "freelancer")
    private Rate receivedRate;

    @OneToMany(mappedBy="customer")
    private Set<Rate> rates;
}
