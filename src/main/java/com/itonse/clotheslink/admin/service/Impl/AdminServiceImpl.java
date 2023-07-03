package com.itonse.clotheslink.admin.service.Impl;

import com.itonse.clotheslink.admin.service.AdminService;
import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.admin.dto.TokenUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.INVALID_TOKEN;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenUserResponse validateToken(String token) {
        try {
            UserVo vo = jwtTokenProvider.getUserInfo(token);

            return TokenUserResponse.builder()
                    .userType(vo.getUserType())
                    .id(vo.getId())
                    .email(vo.getEmail())
                    .build();

        } catch (Exception e) {
            throw new CustomException(INVALID_TOKEN);
        }
    }
}
