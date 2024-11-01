package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.bank.BankAccountDto;
import com.threeChickens.homeService.dto.bank.BankAccountNameDto;
import com.threeChickens.homeService.dto.bank.BankDto;
import com.threeChickens.homeService.entity.BankAccount;
import com.threeChickens.homeService.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BankDto>>> getAll(){
        List<BankDto> getBankDto = bankService.getBanks();
        ApiResponse<List<BankDto>> res = ApiResponse.<List<BankDto>>builder().items(getBankDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/getBankAccountName")
    public ResponseEntity<ApiResponse<BankAccountNameDto>> checkBankAccount(@RequestBody BankAccountDto bankAccountDto) throws Exception {
        String bankAccountName = bankService.getBankAccountName(bankAccountDto);
        BankAccountNameDto bankAccountNameDto = BankAccountNameDto.builder().bankAccountName(bankAccountName).build();
        ApiResponse<BankAccountNameDto> res = ApiResponse.<BankAccountNameDto>builder().items(bankAccountNameDto).build();
        return ResponseEntity.ok(res);
    }
}
