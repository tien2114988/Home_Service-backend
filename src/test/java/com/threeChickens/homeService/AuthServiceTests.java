package com.threeChickens.homeService;

import com.threeChickens.homeService.dto.auth.LoginDto;
import com.threeChickens.homeService.dto.auth.SignUpDto;
import com.threeChickens.homeService.dto.user.GetUserDetailDto;
import com.threeChickens.homeService.exception.AppException;
import com.threeChickens.homeService.exception.StatusCode;
import com.threeChickens.homeService.service.AdminService;
import com.threeChickens.homeService.service.AuthService;
import com.threeChickens.homeService.service.OtpService;
import com.threeChickens.homeService.service.UserService;
import com.threeChickens.homeService.utils.GoogleAuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @Mock
    private UserService userService;
    @Mock private AdminService adminService;
    @Mock private OtpService otpService;
    @Mock private GoogleAuthUtil googleAuthUtil;

    @InjectMocks
    private AuthService authService;
    private LoginDto loginDto;
    private SignUpDto signUpDto;

    @BeforeEach
    void setUp() {
        // Log in
        loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setOtp("123456");

        // Sign up
        signUpDto = new SignUpDto();
        signUpDto.setEmail("test@example.com");
        signUpDto.setOtp("123456");
    }
    @Test
    void testLogInSuccess() {
        // Arrange
        GetUserDetailDto userDto = new GetUserDetailDto();
        userDto.setEmail("test@example.com");

        doNothing().when(otpService).verifyOtp("test@example.com", "123456");
        when(userService.getUserByEmail(loginDto)).thenReturn(userDto);

        // Act
        GetUserDetailDto result = authService.logIn(loginDto);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(otpService).verifyOtp("test@example.com", "123456");
        verify(userService).getUserByEmail(loginDto);
    }
    @Test
    void testLogInOtpVerificationFails() {
        // Arrange
        doThrow(new AppException(StatusCode.OTP_INVALID)).when(otpService).verifyOtp("test@example.com", "123456");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.logIn(loginDto));
        assertEquals(StatusCode.OTP_INVALID.getMessage(), exception.getMessage());

        verify(otpService).verifyOtp("test@example.com", "123456");
        verify(userService, never()).getUserByEmail(any());
    }

    @Test
    void testLogInUserServiceFails() {
        // Arrange
        doNothing().when(otpService).verifyOtp("test@example.com", "123456");
        when(userService.getUserByEmail(loginDto)).thenThrow(new AppException(StatusCode.EMAIL_NOT_FOUND));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.logIn(loginDto));
        assertEquals(StatusCode.EMAIL_NOT_FOUND.getMessage(), exception.getMessage());

        verify(otpService).verifyOtp("test@example.com", "123456");
        verify(userService).getUserByEmail(loginDto);
    }

    /* Sign up */
    @Test
    void testSignUpSuccess() {
        // Arrange
        GetUserDetailDto expectedUser = new GetUserDetailDto();
        expectedUser.setEmail("test@example.com");

        when(userService.existUserByEmail("test@example.com", false)).thenReturn(true);
        doNothing().when(otpService).verifyOtpForAuth("test@example.com", "123456");
        when(userService.createUser(signUpDto)).thenReturn(expectedUser);

        // Act
        GetUserDetailDto result = authService.signUp(signUpDto);

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userService).existUserByEmail("test@example.com", false);
        verify(otpService).verifyOtpForAuth("test@example.com", "123456");
        verify(userService).createUser(signUpDto);
    }

    @Test
    void testSignUpEmailAlreadyExists() {
        // Arrange
        doThrow(new AppException(StatusCode.EMAIL_EXISTED))
                .when(userService).existUserByEmail("test@example.com", false);

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.signUp(signUpDto));
        assertEquals(StatusCode.EMAIL_EXISTED.getMessage(), ex.getMessage());

        verify(userService).existUserByEmail("test@example.com", false);
        verify(otpService, never()).verifyOtpForAuth(any(), any());
        verify(userService, never()).createUser(any());
    }

    @Test
    void testSignUpOtpInvalid() {
        // Arrange
        when(userService.existUserByEmail("test@example.com", false)).thenReturn(true);
        doThrow(new AppException(StatusCode.OTP_INVALID))
                .when(otpService).verifyOtpForAuth("test@example.com", "123456");

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.signUp(signUpDto));
        assertEquals(StatusCode.OTP_INVALID.getMessage(), ex.getMessage());

        verify(userService).existUserByEmail("test@example.com", false);
        verify(otpService).verifyOtpForAuth("test@example.com", "123456");
        verify(userService, never()).createUser(any());
    }
}
