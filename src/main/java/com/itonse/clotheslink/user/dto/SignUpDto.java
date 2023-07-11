package com.itonse.clotheslink.user.dto;

import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.domain.Seller;
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

    public static Seller toSellerEntity(SignUpDto dto) {
        return Seller.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }

    public static Customer toCustomerEntity(SignUpDto dto) {
        return Customer.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }
}
