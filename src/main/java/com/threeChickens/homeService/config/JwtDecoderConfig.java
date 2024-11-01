package com.threeChickens.homeService.config;

import com.threeChickens.homeService.service.AuthService;
import com.threeChickens.homeService.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

@Configuration
public class JwtDecoderConfig implements JwtDecoder {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Override
    public Jwt decode(String token) throws JwtException {
//        try {
//            authService.verify(token);
//        }catch(Exception e) {
//            throw new JwtException(e.getMessage());
//        }

        return jwtUtil.decodeToken().decode(token);
    }
}
