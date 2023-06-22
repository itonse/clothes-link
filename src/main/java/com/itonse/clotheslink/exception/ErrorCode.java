package com.itonse.clotheslink.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTERED_SELLER(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
