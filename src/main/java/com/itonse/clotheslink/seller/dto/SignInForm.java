package com.itonse.clotheslink.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignInForm {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상으로 20자 이하로 입력해주세요.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    public static SignInDto toSignInDto(SignInForm form) {
        return SignInDto.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .build();
    }
}