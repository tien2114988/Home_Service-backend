package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.PackageName;
import com.threeChickens.homeService.enums.PaymentType;
import com.threeChickens.homeService.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(generator = "post-generator")
    @GenericGenerator(name = "post-generator",
            parameters = @Parameter(name = "prefix", value = "POST"),
            strategy = "com.threeChickens.homeService.utils.IdGeneratorUtil")
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String customerNote;

    private Time startTime;

    private int duration;

    private long price;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private boolean isPayment;

    private int totalFreelancer;

    private int numOfFreelancer;

    @Enumerated(EnumType.STRING)
    private PackageName packageName;

    private int totalWorkDay;

    private int numOfWorkedDay;

    private boolean chooseFreelancer;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id", nullable=false)
    private User customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", nullable=false)
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="work_id", nullable=false)
    private Work work;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    private Set<WorkSchedule> workSchedules = new HashSet<>();

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<FreelancerTakePost> freelancerTakePosts = new HashSet<>();

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private HouseCleaning houseCleaning;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Babysitting babysitting;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Rate> rates = new HashSet<>();


}
