package com.itonse.clotheslink.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handlerCustomException (CustomException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(exception.getStatus())
                .errorCode(exception.getErrorCode())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse
                , errorResponse.getErrorCode().getHttpStatus());
    }
}
