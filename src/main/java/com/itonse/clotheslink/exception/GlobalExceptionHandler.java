package com.itonse.clotheslink.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException .class)
    public ResponseEntity<List<String>> handleValidException(
            MethodArgumentNotValidException exception) {

        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors()
                .forEach((error -> errors.add(error.getDefaultMessage())));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException (CustomException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .message(exception.getMessage())
                .build();

        return ResponseEntity
                .status(errorResponse.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleRequestParameterException (
            MissingServletRequestParameterException exception) {

        Map<String, String> map = new HashMap<>();
        map.put("Parameters missing", exception.getParameterName() + " 값을 입력해주세요");
        return ResponseEntity.badRequest().body(map);
    }

}
