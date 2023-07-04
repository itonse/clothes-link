package com.itonse.clotheslink.admin.dto;

import com.itonse.clotheslink.common.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TokenUserResponse {
    private UserType userType;
    private Long id;
    private String email;
}