package com.threeChickens.homeService.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeChickens.homeService.dto.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AuthExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        StatusCode statusCode = StatusCode.UNAUTHENTICATED;

        response.setStatus(statusCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiRes = ApiResponse.builder().returnCode(statusCode.getCode()).message(statusCode.getMessage()).build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiRes));
    }
}
