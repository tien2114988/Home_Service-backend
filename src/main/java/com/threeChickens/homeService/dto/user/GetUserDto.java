package com.threeChickens.homeService.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private String id;
    private String avatar;
    private String email;
    private String role;
    private Date dob;
    private String name;
    private String gender;
    private String status;
    private long balance;
    private String phoneNumber;
    private int reputationPoint;
    private LocalDateTime createdAt;
 }