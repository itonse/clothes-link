package com.itonse.clotheslink.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTERED_SELLER(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다."),
    ALREADY_REGISTERED_CUSTOMER(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다."),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "일치하는 회원정보가 없습니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원 정보를 찾을 수 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않거나 잘못된 토큰입니다."),
    ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "이미 이메일 인증을 완료하였습니다."),
    USER_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "유저 타입이 일치하지 않습니다."),
    NOT_FOUND_USER_TYPE(HttpStatus.BAD_REQUEST, "존재하지 않는 유저타입 입니다."),
    INVALID_AUTH_CODE(HttpStatus.BAD_REQUEST, "잘못된 인증코드 입니다."),
    EXPIRED_AUTH_CODE(HttpStatus.BAD_REQUEST, "만료된 인증코드 입니다."),
    INITIATE_EMAIL_REQUEST(HttpStatus.BAD_REQUEST, "먼저 이메일 요청을 진행해주세요."),
    ALREADY_REGISTERED_CATEGORY(HttpStatus.BAD_REQUEST, "이미 등록된 카테고리 입니다."),
    NOT_EXISTS_CATEGORY(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리 입니다."),
    EMPTY_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "카테고리 이름은 최소 한 글자 이상입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
