package com.threeChickens.homeService.dto.user;

import com.threeChickens.homeService.dto.address.GetAddressDto;
import com.threeChickens.homeService.dto.bank.CreateBankAccountDto;
import com.threeChickens.homeService.dto.bank.GetBankAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDto {
    private String id;
    private String jwt;
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
    private String googleSub;
    private Set<GetAddressDto> addresses;
    private GetBankAccountDto bankAccount;

//    private Set<Post> posts;
//    private Set<TestResult> testResults;
//    private Set<User> notifications;
//    private FreelancerGetPost freelancerGetPost;
//    private FreelancerWorkService freelancerWorkService;
//    private Rate receivedRate;
//    private Set<Rate> rates;
}