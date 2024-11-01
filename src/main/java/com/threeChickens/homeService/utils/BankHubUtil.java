package com.threeChickens.homeService.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.threeChickens.homeService.dto.bankHub.GetFiServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class BankHubUtil {
    @Value("${bankHub.clientId}")
    private String clientId;

    @Value("${bankHub.secretKey}")
    private String secretKey;

    @Value("${bankHub.url}")
    private String bankHubUrl;

    @Value("${bankHub.link}")
    private String bankHubLink;

    @Autowired
    private ObjectMapper objectMapper;

    public GetFiServiceDto getBanks() throws IOException {
        Request request = new Request.Builder()
                .url(bankHubUrl + "/fi-services")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("x-client-id", clientId)
                .addHeader("x-secret-key", secretKey)
                .build();
        Response response = new OkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        String responseBody = response.body().string();

        // Chuyển đổi JSON thành đối tượng GrantTokenDto
        return objectMapper.readValue(responseBody, GetFiServiceDto.class);
    }

//    public LinkDto getLink(String redirectUri) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\n  \"scopes\": \"identity,transaction,balance,transfer\",\n  \"redirectUri\": \""+redirectUri+"\",\n  \"language\": \"vi\"\n}");
//        Request request = new Request.Builder()
//                .url(bankHubUrl +"/grant/token")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .addHeader("x-client-id", clientId)
//                .addHeader("x-secret-key", secretKey)
//                .build();
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Unexpected code " + response);
//        }
//
//        String responseBody = response.body().string();
//
//        // Chuyển đổi JSON thành đối tượng GrantTokenDto
//        GrantTokenDto grantTokenDto = objectMapper.readValue(responseBody, GrantTokenDto.class);
//
//        String link = String.format(bankHubLink +"?grantToken=%s&redirectUri=%s&iframe=false", grantTokenDto.getGrantToken(), redirectUri);
//        return LinkDto.builder().link(link).build();
//    }

//    public BankAccessTokenDto getAccessToken(String publicToken) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\n  \"publicToken\": \""+publicToken+"\"\n}");
//        Request request = new Request.Builder()
//                .url(bankHubUrl + "/grant/exchange")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .addHeader("x-client-id", clientId)
//                .addHeader("x-secret-key", secretKey)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("Unexpected code " + response);
//        }
//
//        String responseBody = response.body().string();
//
//        // Chuyển đổi JSON thành đối tượng GrantTokenDto
//        return objectMapper.readValue(responseBody, BankAccessTokenDto.class);
//    }

//    public IdentityDto getIdentity(String accessToken) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url(bankHubUrl + "/identity")
//                .method("GET", null)
//                .addHeader("Accept", "application/json")
//                .addHeader("x-client-id", clientId)
//                .addHeader("x-secret-key", secretKey)
//                .addHeader("Authorization", accessToken)
//                .build();
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new AppException(StatusCode.RATE_LIMIT);
//        }
//
//        String responseBody = response.body().string();
//
//        System.out.println(responseBody);
//
//        // Chuyển đổi JSON thành đối tượng GrantTokenDto
//        return objectMapper.readValue(responseBody, IdentityDto.class);
//    }



//    private String generateOtp() {
//        Random random = new Random();
//        int otp = 100000 + random.nextInt(900000); // Tạo OTP 6 chữ số
//        return String.valueOf(otp);
//    }

//    public TransferResDto transfer(String accessToken, TransferDto transferDto) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//
//        String description = "Không có lí do";
//        if(transferDto.getDescription()!=null && !transferDto.getDescription().isEmpty()){
//            description = transferDto.getDescription();
//        }
//
//        String reqBody = "{\n" +
//                "  \"fromAccountNumber\": \"" + transferDto.getFromAccountNumber() + "\",\n" +
//                "  \"toBin\": \"" + transferDto.getToBin() + "\",\n" +
//                "  \"toAccountNumber\": \"" + transferDto.getToAccountNumber() + "\",\n" +
//                "  \"amount\": " + transferDto.getAmount() + ",\n" +
//                "  \"description\": \"" + description + "\",\n" +
//                "  \"authCode\": \"" + generateOtp() + "\"\n" +
//                "}";
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType,reqBody );
//        Request request = new Request.Builder()
//                .url(bankHubUrl + "/transfer")
//                .method("POST", body)
//                .addHeader("Accept", "application/json")
//                .addHeader("x-client-id", clientId)
//                .addHeader("x-secret-key", secretKey)
//                .addHeader("Authorization", accessToken)
//                .addHeader("x-interaction-id", UUID.randomUUID().toString())
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//
//        if(!response.isSuccessful()) {
//            System.out.println(response.body().string());
//            return null;
//        }
//
//        String responseBody = response.body().string();
//
//        System.out.println(accessToken);
//        System.out.println(reqBody);
//        System.out.println(responseBody);
//
//        GetTransferDto getTransferDto = objectMapper.readValue(responseBody, GetTransferDto.class);
//
//        System.out.println(getTransferDto.getRequestId());
//        System.out.println(getTransferDto.getTransfer().getRefId());
//
//        // Chuyển đổi JSON thành đối tượng GrantTokenDto
//        return getTransferDto.getTransfer();
//    }

//    public void remove(String accessToken) throws IOException, AppException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
//        Request request = new Request.Builder()
//                .url(bankHubUrl + "/grant/remove")
//                .method("POST", body)
//                .addHeader("Accept", "application/json")
//                .addHeader("x-client-id", clientId)
//                .addHeader("x-secret-key", secretKey)
//                .addHeader("Authorization", accessToken)
//                .build();
//        Response response = client.newCall(request).execute();
//        System.out.println(response.body().string());
//    }

}
