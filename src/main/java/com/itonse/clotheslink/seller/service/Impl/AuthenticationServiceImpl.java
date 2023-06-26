package com.itonse.clotheslink.seller.service.Impl;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;
import com.itonse.clotheslink.seller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_SELLER;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SellerRepository sellerRepository;

    @Override
    public void signUp(SignUpDto dto) {

        Seller seller = SignUpDto.toEntity(dto);

        sellerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        });

        sellerRepository.save(seller);
    }
}
