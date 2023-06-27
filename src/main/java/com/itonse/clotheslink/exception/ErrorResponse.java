package com.itonse.clotheslink.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private ErrorCode errorCode;
    private String message;
}
