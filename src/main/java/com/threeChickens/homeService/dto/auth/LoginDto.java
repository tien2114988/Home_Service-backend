package com.threeChickens.homeService.dto.auth;

import com.threeChickens.homeService.annotation.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Email
    private String email;
//
//    @PasswordConstraint
//    private String password;

    @NotNull(message = "Role must not be null")
    private String role;

    @NotNull(message = "Otp must not be null")
    private String otp;
}
