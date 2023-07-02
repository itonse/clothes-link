package com.itonse.clotheslink.seller.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SendMailDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Request {

        @Email(message = "이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 항목입니다.")
        private String email;

        @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 ~ 최대 20자 이내로 입력해주세요.")
        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Response {
        private String email;
    }
}
