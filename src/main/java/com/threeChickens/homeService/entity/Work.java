package com.threeChickens.homeService.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Work {
    @Id
    @GeneratedValue(generator = "work-generator")
    @GenericGenerator(name = "work-generator",
            parameters = @Parameter(name = "prefix", value = "WORK"),
            strategy = "com.threeChickens.homeService.utils.IdGeneratorUtil")
    private String id;

    private String name;

    private String image;

    private int postPerMonth;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(mappedBy = "work", cascade = CascadeType.ALL)
    private Test test = new Test();

    @OneToMany(mappedBy="work", cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    private Set<FreelancerWorkService> freelancerWorkServices =  new HashSet<>();
}

