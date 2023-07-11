package com.itonse.clotheslink.user.service;

import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.dto.SignInDto;
import com.itonse.clotheslink.user.dto.SignUpDto;
import com.itonse.clotheslink.user.dto.UserInfoResponse;

public interface CustomerAuthService {

    UserInfoResponse signUp(SignUpDto dto);

    String signIn(SignInDto dto);

    Customer findCustomerByToken(String token);
}
