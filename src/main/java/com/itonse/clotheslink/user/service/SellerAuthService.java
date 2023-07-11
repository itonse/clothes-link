package com.itonse.clotheslink.user.service;

import com.itonse.clotheslink.user.domain.Seller;
import com.itonse.clotheslink.user.dto.SignInDto;
import com.itonse.clotheslink.user.dto.UserInfoResponse;
import com.itonse.clotheslink.user.dto.SignUpDto;

public interface SellerAuthService {
    UserInfoResponse signUp(SignUpDto dto);

    String signin(SignInDto dto);

    Seller findSellerByToken(String token);

}
