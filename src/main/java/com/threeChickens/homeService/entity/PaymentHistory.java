package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    private String refId;

    private int amount;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable=false)
    private User user;
}
