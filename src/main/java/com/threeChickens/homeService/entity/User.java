package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.AccountType;
import com.threeChickens.homeService.enums.Gender;
import com.threeChickens.homeService.enums.UserRole;
import com.threeChickens.homeService.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(generator = "user-generator")
    @GenericGenerator(name = "user-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "USER"),
            strategy = "com.threeChickens.homeService.utils.IdGeneratorUtil")
    private String id;

    private String avatar;

    private String email;

    private String firebaseToken;

//    private String password;

    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Date dob;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private long balance;

    private String phoneNumber;

    private int reputationPoint;

    private String googleSub;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private boolean deleted;

    @OneToMany(mappedBy="user")
    private Set<Address> addresses = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BankAccount bankAccount;

    @OneToMany(mappedBy="customer")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "freelancer")
    private Set<FreelancerTakePost> freelancerTakePosts = new HashSet<>();

    @OneToMany(mappedBy = "freelancer")
    private Set<FreelancerWorkService> freelancerWorkServices = new HashSet<>();

    @OneToMany(mappedBy="user")
    private Set<UserNotification> notifications = new HashSet<>();

    @OneToMany(mappedBy="user")
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy="user")
    private Set<PaymentHistory> paymentHistories = new HashSet<>();

//    @OneToMany(mappedBy = "freelancer")
//    private Set<Rate> rates = new HashSet<>();

//    @OneToMany(mappedBy="customer")
//    private Set<Rate> rates = new HashSet<>();
}
