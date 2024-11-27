package com.threeChickens.homeService.service;


import com.nimbusds.jose.JOSEException;
import com.threeChickens.homeService.dto.admin.AdminDto;
import com.threeChickens.homeService.dto.auth.*;
import com.threeChickens.homeService.dto.googleAuth.GoogleLoginDto;
import com.threeChickens.homeService.dto.googleAuth.GoogleSignupDto;
import com.threeChickens.homeService.dto.googleAuth.UserInfoDto;
import com.threeChickens.homeService.dto.user.GetUserDto;
import com.threeChickens.homeService.enums.AccountType;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.utils.GoogleAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

@Service
public class AuthService{
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private GoogleAuthUtil googleAuthUtil;

    public void verifyOtp(VerifyOtpDto otpDto) {
        otpService.verifyOtp(otpDto.getEmail(), otpDto.getOtp());
    }

    public GetUserDto signUp(SignUpDto signUpDto){
        userService.existUserByEmail(signUpDto.getEmail(), false);

        otpService.verifyOtpForAuth(signUpDto.getEmail(), signUpDto.getOtp());

        return userService.createUser(signUpDto);
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

    public GetUserDto logIn(LoginDto loginDto) {
        otpService.verifyOtp(loginDto.getEmail(), loginDto.getOtp());
        return userService.getUserByEmail(loginDto);
    }

    public JwtDto logInForAdmin(AdminLoginDto adminLoginDto) {
        return adminService.checkAdmin(adminLoginDto);
    }

    public GetUserDto verifyJwt(String jwt) throws ParseException, JOSEException {
        return userService.getUserByJwt(jwt);
    }

    public AdminDto verifyJwtForAdmin(String jwt) throws ParseException, JOSEException {
        return adminService.getAdminByJwt(jwt);
    }

    public GoogleLinkDto getGoogleAuthLink(String redirectUri){
        String link = googleAuthUtil.getLink(redirectUri);
        return GoogleLinkDto.builder().url(link).build();
    }

    public GetUserDto logInWithGoogle(GoogleLoginDto googleLoginDto) throws UnsupportedEncodingException {
        UserInfoDto userInfoDto =  googleAuthUtil.getUserInfo(googleLoginDto.getCode(), googleLoginDto.getRedirectUri());
        return userService.getUserByGoogle(userInfoDto);
    }

    public GetUserDto signUpWithGoogle(GoogleSignupDto googleSignupDto){
        return userService.createUserByGoogle(googleSignupDto);
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
