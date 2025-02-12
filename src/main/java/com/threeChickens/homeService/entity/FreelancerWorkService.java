package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.FreelancerWorkStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class FreelancerWorkService {
    @Id
    @GeneratedValue(generator = "req-generator")
    @GenericGenerator(name = "req-generator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "REQ"),
            strategy = "com.threeChickens.homeService.utils.IdGeneratorUtil")
    private String id;

    @Enumerated(EnumType.STRING)
    private FreelancerWorkStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "freelancerWorkService")
    private Set<Rate> rates = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", nullable=false)
    private User freelancer;

    @OneToMany(mappedBy = "freelancerWorkService")
    private Set<Image> images = new HashSet<>();

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id")
//    private Image image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_id", nullable=false)
    private Work work;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_result_id", referencedColumnName = "id")
    private TestResult testResult;
}
