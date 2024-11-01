package com.threeChickens.homeService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeChickens.homeService.dto.bank.BankAccountDto;
import com.threeChickens.homeService.dto.bank.BankDto;
import com.threeChickens.homeService.dto.bankHub.GetFiServiceDto;
import com.threeChickens.homeService.entity.Bank;
import com.threeChickens.homeService.entity.BankAccount;
import com.threeChickens.homeService.entity.User;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.BankAccountRepository;
import com.threeChickens.homeService.repository.BankRepository;
import com.threeChickens.homeService.utils.BankHubUtil;
import com.threeChickens.homeService.utils.VietQrUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class BankService {
    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankHubUtil bankHubUtil;

    @Autowired
    private VietQrUtil vietQrUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public void initBanks() throws IOException {
        if (bankRepository.count() == 0){
            GetFiServiceDto getFiServiceDto = bankHubUtil.getBanks();
            Set<Bank> banks = new HashSet<>();
            getFiServiceDto.getFiServices().forEach(fiServiceDto -> {
                String color = "#79AC78";
                String fiName = fiServiceDto.getFiName();
                if(Objects.equals(fiName, "VPBank")){
                    color = "#009C4D";
                }else if(Objects.equals(fiName, "MBBank")){
                    color = "#141ED2";
                }else if(Objects.equals(fiName, "OCB")){
                    color = "#008C45";
                }else if(Objects.equals(fiName, "VietinBank")){
                    color = "#7DD2F7";
                }else if(Objects.equals(fiName, "Timo Plus")){
                    color = "4F3C8E";
                }else if(Objects.equals(fiName, "ACB")){
                    color = "#204199";
                }else if(Objects.equals(fiName, "BIDV")){
                    color = "#016B69";
                }else if(Objects.equals(fiName, "Techcombank")){
                    color = "#ED1B24";
                }else if(Objects.equals(fiName, "Vietcombank")){
                    color = "#005030";
                }else if(Objects.equals(fiName, "Agribank")){
                    color = "#AF1B3F";
                }else if(Objects.equals(fiName, "TPBank")){
                    color = "#602E88";
                }else if(Objects.equals(fiName, "Eximbank")){
                    color = "#005D98";
                }else if(Objects.equals(fiName, "MSB")){
                    color = "#DA2128";
                }

                Bank bank = modelMapper.map(fiServiceDto, Bank.class);
                bank.setColor(color);
                bank.setBin(fiServiceDto.getFiBin());
                bank.setFiName(fiServiceDto.getId());
                banks.add(bank);
            });
            bankRepository.saveAll(banks);
        }
    }

    public List<BankDto> getBanks(){
        List<Object[]> banks = bankRepository.findDistinctFiNameAndFiBin();
        return banks.stream().map(
                bank -> BankDto.builder().fiName((String)bank[0]).bin((String)bank[1]).logo((String)bank[2]).build()
        ).toList();
    }

    public String getBankAccountName(BankAccountDto bankAccountDto) throws Exception {
        return vietQrUtil.lookup(bankAccountDto);
    }

    public void updateBankAccountForUser(User user, BankAccountDto bankAccountDto) throws Exception {
        BankAccount bankAccount = user.getBankAccount();

        Bank bank = bankRepository.findFirstByBin(bankAccountDto.getBin()+"").orElseThrow(
                () -> new AppException(StatusCode.BANK_NOT_FOUND)
        );
        String bankAccountName = vietQrUtil.lookup(bankAccountDto);

        bankAccount.setAccountName(bankAccountName);
        bankAccount.setUser(user);
        bankAccount.setAdmin(false);

        bankAccount.setBank(bank);
        bankAccountRepository.save(bankAccount);
    }

}
