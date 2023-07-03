package com.itonse.clotheslink.customer.service;

import com.itonse.clotheslink.customer.dto.SignInDto;
import com.itonse.clotheslink.customer.dto.SignUpDto;
import com.itonse.clotheslink.customer.dto.UserInfoResponse;

public interface CustomerAuthService {

    UserInfoResponse signUp(SignUpDto dto);

    String signIn(SignInDto dto);

    UserInfoResponse sendAuthMail(String token);
}
