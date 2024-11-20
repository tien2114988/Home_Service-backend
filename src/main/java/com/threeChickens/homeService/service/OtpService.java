package com.threeChickens.homeService.service;

import com.threeChickens.homeService.dto.auth.VerifyOtpDto;
import com.threeChickens.homeService.entity.Otp;
import com.threeChickens.homeService.enums.AccountType;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.repository.OtpRepository;
import com.threeChickens.homeService.repository.UserRepository;
import com.threeChickens.homeService.utils.EmailUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UserService userService;

    private static final long OTP_VALID_DURATION = 60*5; // 5m

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo OTP 6 chữ số
        return String.valueOf(otp);
    }

    @Transactional
    public void sendOtp(String email) {
        String otp = generateOtp();
        Otp otpEntity = Otp.builder().email(email).otp(otp).verified(false).build();

        boolean isSignUp = !userService.existUserByEmail(email, AccountType.EMAIL,true);

        if(isSignUp){
            otpRepository.deleteAllByEmail(email);
        }

        otpRepository.save(otpEntity);

        String message = "Mã OTP của bạn là: " + otp;
        emailUtil.sendEmail(email, "OTP Code", message, false);
    }

    public void verifyOtp(VerifyOtpDto verifyOtpDto) {
        Otp otp = otpRepository.findFirstByEmailAndOtpOrderByCreatedDateDesc(verifyOtpDto.getEmail(), verifyOtpDto.getOtp()).orElseThrow(
                ()->new AppException(StatusCode.OTP_INVALID)
        );

        if (otp.getCreatedDate().plusSeconds(OTP_VALID_DURATION).isBefore(LocalDateTime.now())) {
            throw new AppException(StatusCode.EXPIRED_OTP);
        }

        boolean isSignUp = !userService.existUserByEmail(verifyOtpDto.getEmail(), AccountType.EMAIL,true);

        if(isSignUp){
            emailHasSignUp(verifyOtpDto.getEmail());
        }else if(otp.isVerified()){
            throw new AppException(StatusCode.OTP_EXISTED);
        }

        otp.setVerified(true);
        otpRepository.save(otp);
    }

    public void verifyOtpForAuth(String email, String otp) {
        Optional<Otp> otpOptional = otpRepository.findFirstByEmailAndOtpOrderByCreatedDateDesc(email, otp);
        if (otpOptional.isEmpty()) {
            throw new AppException(StatusCode.OTP_INVALID);
        }

        Otp otpEntity = otpOptional.get();
        if (!otpEntity.isVerified()) {
            throw new AppException(StatusCode.OTP_NOT_FOUND);
        }

        if (otpEntity.getCreatedDate().plusSeconds(OTP_VALID_DURATION).isBefore(LocalDateTime.now())) {
            throw new AppException(StatusCode.EXPIRED_OTP);
        }
    }

    public void cleanupOtp() {
        otpRepository.deleteAll(otpRepository.findAll().stream()
                .filter(otp -> otp.getCreatedDate().plusSeconds(OTP_VALID_DURATION).isBefore(LocalDateTime.now()))
                .collect(Collectors.toList()));
    }

    public void emailHasSignUp(String email) {
        Otp otp = otpRepository.findFirstByEmailAndVerifiedIsTrueOrderByCreatedDateDesc(email).orElse(null);
        if(otp != null && !otp.getCreatedDate().plusSeconds(OTP_VALID_DURATION).isBefore(LocalDateTime.now())) {
            throw new AppException(StatusCode.EMAIL_SIGN_UP);
        }
    }
}
