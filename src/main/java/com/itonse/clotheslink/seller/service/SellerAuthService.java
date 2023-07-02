package com.itonse.clotheslink.seller.service;

import com.itonse.clotheslink.seller.dto.SendMailDto;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpResponse;
import com.itonse.clotheslink.seller.dto.SignUpDto;

public interface SellerAuthService {
    SignUpResponse signUp(SignUpDto dto);

    String signin(SignInDto dto);

    SendMailDto.Response sendAuthMail(SendMailDto.Request email);
}
