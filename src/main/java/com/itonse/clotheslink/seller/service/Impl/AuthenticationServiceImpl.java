package com.itonse.clotheslink.seller.service.Impl;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;
import com.itonse.clotheslink.seller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_SELLER;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final SellerRepository sellerRepository;

    @Override
    public void SignUp(SignUpDto dto) {

        Seller seller = SignUpDto.toEntity(dto);

        Optional<Seller> existSeller = sellerRepository.findByEmail(dto.getEmail());

        if (existSeller.isPresent()) {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        }

        sellerRepository.save(seller);
    }
}
