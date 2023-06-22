package com.itonse.clotheslink.seller.dto;

import com.itonse.clotheslink.seller.domain.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpDto {
    private String email;
    private String password;
    private String phone;

    public static Seller toEntity(SignUpDto dto) {
        return Seller.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }
}
