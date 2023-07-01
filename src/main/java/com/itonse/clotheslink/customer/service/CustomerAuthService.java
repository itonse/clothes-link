package com.itonse.clotheslink.customer.service;

import com.itonse.clotheslink.customer.dto.SignInDto;
import com.itonse.clotheslink.customer.dto.SignUpDto;
import com.itonse.clotheslink.customer.dto.SignUpResponse;

public interface CustomerAuthService {

    SignUpResponse signUp(SignUpDto dto);

    String signIn(SignInDto dto);
}
