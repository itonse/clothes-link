package com.itonse.clotheslink.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int status;
    private ErrorCode errorCode;
    private String message;
}
