package com.itonse.clotheslink.customer.dto;

import com.itonse.clotheslink.customer.domain.Customer;
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
    private String name;
    private String password;
    private String phone;

    public static Customer toCustomerEntity(SignUpDto dto) {
        return Customer.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }
}
