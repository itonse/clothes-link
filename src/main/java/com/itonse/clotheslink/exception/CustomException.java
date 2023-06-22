package com.itonse.clotheslink.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private int status;
    private String message;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.status = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
}
