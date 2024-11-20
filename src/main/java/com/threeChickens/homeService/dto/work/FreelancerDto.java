package com.threeChickens.homeService.dto.work;

import com.threeChickens.homeService.dto.address.GetAddressDto;
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
public class FreelancerDto {
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

    private Set<GetAddressDto> addresses;
}
