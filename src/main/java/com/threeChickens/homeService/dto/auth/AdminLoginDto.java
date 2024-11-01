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
public class AdminLoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
