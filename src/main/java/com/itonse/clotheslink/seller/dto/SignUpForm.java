package com.itonse.clotheslink.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpForm {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Size(min = 8, message = "비밀번호는 8자 이상으로 입력해주세요.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    @NotBlank(message = "연락처는 필수 항목 입니다.")
    private String phone;

    public static SignUpDto ToSignUpDto(SignUpForm form) {
        return SignUpDto.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .build();
    }
}
