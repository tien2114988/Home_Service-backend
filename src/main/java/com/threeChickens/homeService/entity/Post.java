package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

    @UpdateTimestamp
    private ZonedDateTime updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

    private String customerNote;

    private Time startTime;

    private int duration;

    private long price;

    private String status;

    private String paymentType;

    private boolean isPayment;

    private int totalFreelancer;

    private int numOfFreelancer;

    private String packageName;

    private int totalWorkDay;

    private int numOfWorkedDay;

    private boolean chooseFreelancer;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private User customer;

    @ManyToOne
    @JoinColumn(name="service_id", nullable=false)
    private Service service;

    @OneToMany(mappedBy="post")
    private Set<WorkSchedule> workSchedules;

    @OneToMany(mappedBy="post")
    private Set<Notification> notifications;

    @OneToOne(mappedBy = "post")
    private FreelancerGetPost freelancerGetPost;

    @OneToOne(mappedBy = "post")
    private HouseCleaning houseCleaning;

    @OneToOne(mappedBy = "post")
    private Babysitting babysitting;

    @OneToOne(mappedBy = "post")
    private Rate rate;
}
