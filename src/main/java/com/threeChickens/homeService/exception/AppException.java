package com.threeChickens.homeService.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    private StatusCode code;

    public AppException(StatusCode code){
        super(code.getMessage());
        this.code = code;
    }
}
