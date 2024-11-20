package com.threeChickens.homeService.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBankAccountDto {
    private String accountNumber;
    private BankDto bank;
}
