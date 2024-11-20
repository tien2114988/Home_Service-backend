package com.threeChickens.homeService.utils;

import com.threeChickens.homeService.dto.bank.CreateBankAccountDto;
import com.threeChickens.homeService.dto.vietQr.LookUpDto;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;


@Component
public class VietQrUtil {
    @Value("${vietQr.clientId}")
    private String clientId;

    @Value("${vietQr.apiKey}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String lookup(CreateBankAccountDto createBankAccountDto) throws Exception {
        String url = "https://api.vietqr.io/v2/lookup/";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-client-id",clientId);
        headers.set("x-api-key",apiKey);

        HttpEntity<CreateBankAccountDto> request = new HttpEntity<>(createBankAccountDto, headers);
        LookUpDto lookupDto = restTemplate.postForObject(url, request, LookUpDto.class);
        if(!lookupDto.getCode().equals("00")){
            throw new AppException(StatusCode.BANK_ACCOUNT_NOT_FOUND);
        }

        return lookupDto.getData().getAccountName();
    }
}
