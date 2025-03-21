package com.threeChickens.homeService.controller;

import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.bank.CreateBankAccountDto;
import com.threeChickens.homeService.dto.bank.BankAccountNameDto;
import com.threeChickens.homeService.dto.bank.BankDto;
import com.threeChickens.homeService.dto.notification.RedisNotificationDto;
import com.threeChickens.homeService.service.BankService;
import com.threeChickens.homeService.utils.ListRedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Bank", description = "APIs for Bank and Bank Account")
@RequestMapping("api/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping
    @Operation(summary = "Get all banks")
    public ResponseEntity<ApiResponse<List<BankDto>>> getAll(){
        List<BankDto> getBankDto = bankService.getBanks();
        ApiResponse<List<BankDto>> res = ApiResponse.<List<BankDto>>builder().items(getBankDto).build();
        return ResponseEntity.ok(res);
    }


    @PostMapping("/getBankAccountName")
    @Operation(summary = "Get bank account name", description = "Pass bin and account number to get bank account name")
    public ResponseEntity<ApiResponse<BankAccountNameDto>> checkBankAccount(@RequestBody CreateBankAccountDto createBankAccountDto) throws Exception {

        String bankAccountName = bankService.getBankAccountName(createBankAccountDto);
        BankAccountNameDto bankAccountNameDto = BankAccountNameDto.builder().bankAccountName(bankAccountName).build();
        ApiResponse<BankAccountNameDto> res = ApiResponse.<BankAccountNameDto>builder().items(bankAccountNameDto).build();
        return ResponseEntity.ok(res);
    }
}
