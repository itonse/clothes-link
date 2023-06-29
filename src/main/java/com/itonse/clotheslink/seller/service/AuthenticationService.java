package com.itonse.clotheslink.seller.service;

import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpDto;

public interface AuthenticationService {
    void signUp(SignUpDto dto);

    String loginToken(SignInDto request);

    String findTokenUser(Long id, String email);
}
