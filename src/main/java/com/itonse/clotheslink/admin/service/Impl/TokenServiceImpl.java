package com.itonse.clotheslink.admin.service.Impl;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.customer.domain.Customer;
import com.itonse.clotheslink.customer.repository.CustomerRepository;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.admin.dto.UserInfo;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserInfo validateToken(String token) {
        try {
            UserVo vo = jwtTokenProvider.getUserInfo(token);

            return UserInfo.builder()
                    .userType(vo.getUserType())
                    .id(vo.getId())
                    .email(vo.getEmail())
                    .build();

        } catch (Exception e) {
            throw new CustomException(INVALID_TOKEN);
        }
    }

    @Override
    public Seller findSellerByToken(String token) {
        this.validateToken(token);

        UserVo vo = jwtTokenProvider.getUserInfo(token);

        Seller seller = sellerRepository.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (!vo.getUserType().equals(UserType.SELLER)) {
            throw new CustomException(USER_TYPE_MISMATCH);
        }

        return seller;
    }

    @Override
    public Customer findCustomerByToken(String token) {
        this.validateToken(token);

        UserVo vo = jwtTokenProvider.getUserInfo(token);

        Customer customer = customerRepository.findByIdAndEmail(vo.getId(), vo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (!vo.getUserType().equals(UserType.CUSTOMER)) {
            throw new CustomException(USER_TYPE_MISMATCH);
        }

        return customer;
    }

}
