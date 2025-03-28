package com.threeChickens.homeService.dto.auth;


//import com.taekwondo.annotation.PasswordMatches;


import com.threeChickens.homeService.annotation.PasswordConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {
    @Email
    private String email;

    @NotNull(message = "Password must not be null")
    private String role;

    @NotNull(message = "Password must not be null")
    @PasswordConstraint
    private String password;

    @NotNull(message = "Otp must not be null")
    private String otp;
}
