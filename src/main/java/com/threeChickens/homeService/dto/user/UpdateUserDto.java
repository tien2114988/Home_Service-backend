package com.threeChickens.homeService.dto.user;

import com.threeChickens.homeService.dto.bank.CreateBankAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    private String avatar;
    private Date dob;
    private String name;
    private String gender;
    private String status;
    private Long balance;
    private String phoneNumber;
    private Integer reputationPoint;

    private CreateBankAccountDto bankAccount;
}
