package com.threeChickens.homeService;

import com.threeChickens.homeService.entity.Otp;
import com.threeChickens.homeService.repository.OtpRepository;
import com.threeChickens.homeService.service.OtpService;
import com.threeChickens.homeService.service.UserService;
import com.threeChickens.homeService.utils.EmailUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OtpServiceTests {
    @Mock
    private OtpRepository otpRepository;
    @Mock
    private EmailUtil emailUtil;
    @Mock
    private UserService userService;

    @InjectMocks
    @Spy
    private OtpService otpService;
    @Captor
    ArgumentCaptor<Otp> otpCaptor;
    private static final long OTP_VALID_DURATION = 60*50;
    private final String email = "test@example.com";
    @Test
    void testSendOtp_ForNewUser_ShouldDeleteOldAndSendEmail() {
        // Arrange
        when(userService.existUserByEmail(email, true)).thenReturn(false);

        // Act
        otpService.sendOtp(email);

        // Assert
        verify(userService).existUserByEmail(email, true);
        verify(otpRepository).deleteAllByEmail(email);
        verify(otpRepository).save(otpCaptor.capture());
        verify(emailUtil).sendEmail(
                eq(email),
                eq("OTP Code"),
                argThat(message -> message.startsWith("Mã OTP của bạn là: ")),
                eq(false)
        );
        Otp savedOtp = otpCaptor.getValue();
        assertEquals(email, savedOtp.getEmail());
        assertEquals(6, savedOtp.getOtp().length());
        assertFalse(savedOtp.isVerified());
    }

    @Test
    void testSendOtp_ForExistingUser_ShouldNotDeleteOld() {
        // Arrange
        when(userService.existUserByEmail(email, true)).thenReturn(true);

        // Act
        otpService.sendOtp(email);

        // Assert
        verify(userService).existUserByEmail(email, true);
        verify(otpRepository, never()).deleteAllByEmail(email);
        verify(otpRepository).save(otpCaptor.capture());
        verify(emailUtil).sendEmail(
                eq(email),
                eq("OTP Code"),
                argThat(message -> message.startsWith("Mã OTP của bạn là: ")),
                eq(false)
        );

        Otp savedOtp = otpCaptor.getValue();
        assertEquals(email, savedOtp.getEmail());
        assertEquals(6, savedOtp.getOtp().length());
        assertFalse(savedOtp.isVerified());
    }
}
