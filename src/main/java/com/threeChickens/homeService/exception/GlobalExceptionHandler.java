package com.threeChickens.homeService.exception;

import com.threeChickens.homeService.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e){
        StatusCode statusCode = StatusCode.UNCATEGORIZED;
        ApiResponse<?> res = ApiResponse.builder().message(e.getMessage()).returnCode(statusCode.getCode()).build();
        return ResponseEntity.status(statusCode.getHttpStatusCode()).body(res);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handleAccessDeniedException(AccessDeniedException e){
        StatusCode statusCode = StatusCode.NO_PERMISSION;
        ApiResponse<?> res = ApiResponse.builder().message(statusCode.getMessage()).returnCode(statusCode.getCode()).build();
        return ResponseEntity.status(statusCode.getHttpStatusCode()).body(res);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handleAppException(AppException e){
        StatusCode statusCode = e.getCode();
        ApiResponse<?> res = ApiResponse.builder().message(statusCode.getMessage()).returnCode(statusCode.getCode()).build();
        return ResponseEntity.status(statusCode.getHttpStatusCode()).body(res);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        StatusCode statusCode = StatusCode.INVALIDATION;
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        ApiResponse<?> res = ApiResponse.builder().message(message).returnCode(statusCode.getCode()).build();
        return ResponseEntity.status(statusCode.getHttpStatusCode()).body(res);
    }
}
