package com.threeChickens.homeService.dto.user;

import com.threeChickens.homeService.dto.bank.BankAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String jwt;
    private String avatar;
    private String email;
    private String role;
    private Date dob;
    private String name;
    private char gender;
    private String status;
    private long balance;
    private String phoneNumber;
    private int reputationPoint;

    private Set<AddressDto> addresses;
    private BankAccountDto bankAccount;

//    private Set<Post> posts;
//    private Set<TestResult> testResults;
//    private Set<User> notifications;
//    private FreelancerGetPost freelancerGetPost;
//    private FreelancerWorkService freelancerWorkService;
//    private Rate receivedRate;
//    private Set<Rate> rates;
}