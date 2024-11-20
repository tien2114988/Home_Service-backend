package com.threeChickens.homeService.controller;

import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.ApiResponse;
import com.threeChickens.homeService.dto.admin.AdminDto;
import com.threeChickens.homeService.dto.auth.*;
import com.threeChickens.homeService.dto.googleAuth.GoogleLoginDto;
import com.threeChickens.homeService.dto.googleAuth.GoogleSignupDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Map;

@RestController
@Tag(name = "Authentication", description = "APIs for Log In and Sign Up (sendOtp => verifyOtp => login/signup)")
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    @Operation(summary = "Sign Up for Customer and Freelancer",
            description = "Called after sendOtp and verifyOtp.")
    public ResponseEntity<ApiResponse<GetUserDto>> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        GetUserDto getUserDto = authService.signUp(signUpDto);
        ApiResponse<GetUserDto> res = ApiResponse.<GetUserDto>builder().items(getUserDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/verifyOtp")
    @Operation(summary = "Verify Otp for User to Login and SignUp",
            description = "Called after sendOtp, check otp is valid, expired")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody @Valid VerifyOtpDto verifyOtpDto){
        authService.verifyOtp(verifyOtpDto);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PostMapping("/sendOtp")
    @Operation(summary = "Send Otp for User to Login and SignUp",
            description = "Pass role if login, don't pass role if signup")
    public ResponseEntity<ApiResponse<?>> sendOtp(@RequestBody @Valid OtpDto otpDto) throws MessagingException {
        authService.sendOtp(otpDto);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @GetMapping("/google/getLink")
    @Operation(summary = "Get Google link for Login",
            description = "Return a link")
    public ResponseEntity<ApiResponse<GoogleLinkDto>> getGoogleLink(@RequestParam("redirectUri") String redirectUri) {
        GoogleLinkDto googleLinkDto = authService.getGoogleAuthLink(redirectUri);
        ApiResponse<GoogleLinkDto> res = ApiResponse.<GoogleLinkDto>builder().items(googleLinkDto).build();
        return ResponseEntity.ok(res);
    }


    @PostMapping("/google/signUp")
    @Operation(summary = "Create account by login google",
            description = "Call after signUp by Google, pass user data and role to create user")
    public ResponseEntity<ApiResponse<GetUserDto>> signUpWithGoogle(@RequestBody GoogleSignupDto googleSignupDto) throws UnsupportedEncodingException {
        GetUserDto getUserDto = authService.signUpWithGoogle(googleSignupDto);
        ApiResponse<GetUserDto> res = ApiResponse.<GetUserDto>builder().items(getUserDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/google/logIn")
    @Operation(summary = "Both logIn and signUp by Google",
            description = "if login : return jwt and user data, if signup : only return user data (pass user data and role to signUp with Google to create account)")
    public ResponseEntity<ApiResponse<GetUserDto>> logInWithGoogle(@RequestBody GoogleLoginDto googleLoginDto) throws UnsupportedEncodingException {
        GetUserDto getUserDto = authService.logInWithGoogle(googleLoginDto);
        ApiResponse<GetUserDto> res = ApiResponse.<GetUserDto>builder().items(getUserDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/logIn")
    @Operation(summary = "Log in for User",
            description = "Called after sendOtp, check otp is valid, expired")
    public ResponseEntity<ApiResponse<GetUserDto>> logIn(@RequestBody @Valid LoginDto loginDto) {
        GetUserDto getUserDto = authService.logIn(loginDto);
        ApiResponse<GetUserDto> res = ApiResponse.<GetUserDto>builder().items(getUserDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/logInForAdmin")
    @Operation(summary = "Log in for Admin")
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

    @PostMapping("/verifyJwtForUser")
    @Operation(summary = "Verify Jwt for Admin", description = "Check jwt to know whether navigate to login page or homepage")
    public ResponseEntity<ApiResponse<GetUserDto>> verifyJwtUser(@RequestBody JwtDto jwtDto) throws ParseException, JOSEException {
        GetUserDto getUserDto = authService.verifyJwt(jwtDto.getJwt());
        ApiResponse<GetUserDto> res = ApiResponse.<GetUserDto>builder().items(getUserDto).build();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/verifyJwtForAdmin")
    @Operation(summary = "Verify Jwt for Admin", description = "Check jwt to know whether navigate to login page or homepage")
    public ResponseEntity<ApiResponse<AdminDto>> verifyJwtForAdmin(@RequestBody JwtDto jwtDto) throws ParseException, JOSEException {
        AdminDto adminDto = authService.verifyJwtForAdmin(jwtDto.getJwt());
        ApiResponse<AdminDto> res = ApiResponse.<AdminDto>builder().items(adminDto).build();
        return ResponseEntity.ok(res);
    }
}

