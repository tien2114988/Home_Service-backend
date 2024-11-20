package com.threeChickens.homeService.dto.googleAuth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String sub;
    private String name;
    private String picture;
    private String email;
}
