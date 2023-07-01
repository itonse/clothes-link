package com.itonse.clotheslink.seller.service.Impl;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpResponse;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.dto.TokenUserResponse;
import com.itonse.clotheslink.seller.repository.SellerRepository;
import com.itonse.clotheslink.seller.service.SellerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class SellerAuthServiceImpl implements SellerAuthService {

    private final SellerRepository sellerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public SignUpResponse signUp(SignUpDto dto) {

        Seller seller = SignUpDto.toSellerEntity(dto);

        sellerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        });

        sellerRepository.save(seller);

        return SignUpResponse.builder()
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
    public TokenUserResponse validateToken(String token) {
        try {
            UserVo vo = jwtTokenProvider.getUserInfo(token);
            Seller seller = sellerRepository.findByIdAndEmail(vo.getId(), vo.getEmail())
                    .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
            return TokenUserResponse.builder()
                    .id(seller.getId())
                    .email(seller.getEmail())
                    .build();

        } catch (Exception e) {
            throw new CustomException(INVALID_TOKEN);
        }
    }

}
