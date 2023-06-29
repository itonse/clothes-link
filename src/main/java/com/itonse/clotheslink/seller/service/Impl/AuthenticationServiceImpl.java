package com.itonse.clotheslink.seller.service.Impl;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignIn;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;
import com.itonse.clotheslink.seller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SellerRepository sellerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void signUp(SignUpDto dto) {

        Seller seller = SignUpDto.toEntity(dto);

        sellerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        });

        sellerRepository.save(seller);
    }

    @Override
    public String loginToken(SignInDto request) {
        Seller seller = sellerRepository.findByEmailAndPassword(
                request.getEmail(), request.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_FAIL));

        String token =  jwtTokenProvider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
        return SignIn.response(token);
    }

    @Override
    public String findTokenUser(Long id, String email) {
        Seller seller = sellerRepository.findByIdAndEmail(id, email)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return seller.getEmail();
    }

}
