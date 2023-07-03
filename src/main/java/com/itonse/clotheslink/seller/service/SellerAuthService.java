package com.itonse.clotheslink.seller.service;

import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.UserInfoResponse;
import com.itonse.clotheslink.seller.dto.SignUpDto;

public interface SellerAuthService {
    UserInfoResponse signUp(SignUpDto dto);

    String signin(SignInDto dto);

    UserInfoResponse sendAuthMail(String token);
}
