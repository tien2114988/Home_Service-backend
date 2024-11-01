package com.threeChickens.homeService.controller;

import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.auth.*;
import com.threeChickens.homeService.dto.user.UserDto;
import com.threeChickens.homeService.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<?>> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        authService.signUp(signUpDto);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody @Valid VerifyOtpDto verifyOtpDto){
        authService.verifyOtp(verifyOtpDto);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<ApiResponse<?>> sendOtp(@RequestBody @Valid OtpDto otpDto) throws MessagingException {
        authService.sendOtp(otpDto);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/logIn")
    public ResponseEntity<ApiResponse<UserDto>> logIn(@RequestBody @Valid LoginDto loginDto) {
        UserDto userDto = authService.logIn(loginDto);
        ApiResponse<UserDto> res = ApiResponse.<UserDto>builder().items(userDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/logInForAdmin")
    public ResponseEntity<ApiResponse<JwtDto>> logInForAdmin(@RequestBody @Valid AdminLoginDto adminLoginDto) {
        JwtDto jwtDto = authService.logInForAdmin(adminLoginDto);
        ApiResponse<JwtDto> res = ApiResponse.<JwtDto>builder().items(jwtDto).build();
        return ResponseEntity.ok(res);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse<?>> logout(@RequestBody JwtDto jwtDto) throws ParseException, JOSEException {
//        authService.logout(jwtDto.getJwt());
//        return ResponseEntity.ok(ApiResponse.builder().build());
//    }

    @PostMapping("/verifyJwt")
    public ResponseEntity<ApiResponse<UserDto>> verifyJwt(@RequestBody JwtDto jwtDto) throws ParseException, JOSEException {
        UserDto userDto = authService.verifyJwt(jwtDto.getJwt());
        ApiResponse<UserDto> res = ApiResponse.<UserDto>builder().items(userDto).build();
        return ResponseEntity.ok(res);
    }
}

