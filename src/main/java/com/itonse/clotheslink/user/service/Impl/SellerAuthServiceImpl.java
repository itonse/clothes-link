package com.itonse.clotheslink.user.service.Impl;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.user.domain.Seller;
import com.itonse.clotheslink.user.dto.SignInDto;
import com.itonse.clotheslink.user.dto.UserInfoResponse;
import com.itonse.clotheslink.user.dto.SignUpDto;
import com.itonse.clotheslink.user.repository.SellerRepository;
import com.itonse.clotheslink.user.service.SellerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class SellerAuthServiceImpl implements SellerAuthService {

    private final SellerRepository sellerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public UserInfoResponse signUp(SignUpDto dto) {

        Seller seller = SignUpDto.toSellerEntity(dto);

        sellerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        });

        sellerRepository.save(seller);

        return UserInfoResponse.builder()
                .id(seller.getId())
                .email(seller.getEmail())
                .build();
    }

    @Override
    public String signin(SignInDto dto) {
        Seller seller = sellerRepository.findByEmailAndPassword(
                dto.getEmail(), dto.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_FAIL));

        return jwtTokenProvider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
    }

    @Override
    public Seller findSellerByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(INVALID_TOKEN);
        }

        UserVo vo = jwtTokenProvider.getUserInfo(token);

        if (!vo.getUserType().equals(UserType.SELLER)) {
            throw new CustomException(USER_TYPE_MISMATCH);
        }

        return sellerRepository.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
    }

    @Override
    public Seller validateSeller(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(INVALID_TOKEN);
        }

        Seller seller = findSellerByToken(token);

        if (!seller.isAuthenticated()) {
            throw new CustomException(UNAUTHORIZED_USER);
        }

        return seller;
    }
}
