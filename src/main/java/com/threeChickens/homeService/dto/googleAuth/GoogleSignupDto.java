package com.threeChickens.homeService.dto.googleAuth;

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
public class GoogleSignupDto {
    @Email
    private String email;

    @NotNull(message = "Name must not be null")
    private String name;

    private String avatar;

    @NotNull(message = "Sub must not be null")
    private String googleSub;

    @NotNull(message = "Role must not be null")
    private String role;
}
