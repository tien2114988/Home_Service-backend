package com.threeChickens.homeService.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeChickens.homeService.dto.firebase.FirebaseResDto;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;


@Component
public class FirebaseUtil {
    private final String PUSH_URL = "https://exp.host/--/api/v2/push/send";

    @Autowired
    private ObjectMapper objectMapper;

    public void sendNotification(String token, String title, String body) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Tạo payload cho thông báo
        String payload = String.format("{\"to\":\"%s\",\"title\":\"%s\",\"body\":\"%s\"}", token, title, body);

        // Thiết lập headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo HttpEntity
        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(PUSH_URL, request, String.class);

        FirebaseResDto firebaseResponse = objectMapper.readValue(response.getBody(), FirebaseResDto.class);

        if(Objects.equals(firebaseResponse.getData().getStatus(), "error")){
            throw new RuntimeException(firebaseResponse.getData().getMessage());
        }
    }
}
