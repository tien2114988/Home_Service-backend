package com.threeChickens.homeService.entity;

import com.threeChickens.homeService.enums.TakePostStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FreelancerTakePost {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private TakePostStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "freelancer_id", nullable=false)
    private User freelancer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable=false)
    private Post post;
}
