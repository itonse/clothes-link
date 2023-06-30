package com.itonse.clotheslink.seller.service;

import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpResponse;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.dto.TokenUserResponse;

public interface AuthenticationService {
    SignUpResponse signUp(SignUpDto dto);

    String signin(SignInDto dto);

    TokenUserResponse validateToken(String token);
}
