package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    private String title;

    private boolean isView;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    @ManyToMany
    private Set<User> users = new HashSet<>();;
}
