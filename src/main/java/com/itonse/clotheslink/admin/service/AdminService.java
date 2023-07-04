package com.itonse.clotheslink.admin.service;

import com.itonse.clotheslink.admin.dto.TokenUserResponse;

public interface AdminService {

    TokenUserResponse validateToken(String token);
}
