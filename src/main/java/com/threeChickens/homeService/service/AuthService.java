package com.threeChickens.homeService.service;


import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.auth.*;
import com.threeChickens.homeService.dto.user.UserDto;
import com.threeChickens.homeService.exception.AppException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class AuthService{
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private OtpService otpService;

    public void verifyOtp(VerifyOtpDto otpDto) {
        otpService.verifyOtp(otpDto);
    }

    public void signUp(SignUpDto signUpDto){
        userService.existUserByEmail(signUpDto.getEmail(), false);

        otpService.verifyOtpForAuth(signUpDto.getEmail(), signUpDto.getOtp());

        userService.createUser(signUpDto);
    }

    public void sendOtp(OtpDto otpDto) throws AppException {
        String email = otpDto.getEmail();
        if(otpDto.getRole()!=null){
            userService.existUserByEmailAndRole(otpDto);
        }else{
            userService.existUserByEmail(email, false);
            otpService.emailHasSignUp(email);
        }
        otpService.sendOtp(email);
    }

    public UserDto logIn(LoginDto loginDto) {
        otpService.verifyOtpForAuth(loginDto.getEmail(), loginDto.getOtp());
        return userService.getUserByEmailAndPassword(loginDto);
    }

    public JwtDto logInForAdmin(AdminLoginDto adminLoginDto) {
        return adminService.checkAdmin(adminLoginDto);
    }

    public UserDto verifyJwt(String jwt) throws ParseException, JOSEException {
        return userService.getUserByJwt(jwt);
    }

//    public void logout(String token) throws ParseException, JOSEException {
//        SignedJWT signedJWT = jwtUtil.verifyToken(token);
//
//        String jit = signedJWT.getJWTClaimsSet().getJWTID();
//        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        InvalidToken invalidToken = new InvalidToken(jit, expiryTime);
//
//        tokenRepo.save(invalidToken);
//    }
}
