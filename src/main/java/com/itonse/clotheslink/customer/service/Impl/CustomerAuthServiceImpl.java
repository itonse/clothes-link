package com.itonse.clotheslink.customer.service.Impl;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.customer.domain.Customer;
import com.itonse.clotheslink.customer.dto.SignInDto;
import com.itonse.clotheslink.customer.dto.SignUpDto;
import com.itonse.clotheslink.customer.dto.SignUpResponse;
import com.itonse.clotheslink.customer.repository.CustomerRepository;
import com.itonse.clotheslink.customer.service.CustomerAuthService;
import com.itonse.clotheslink.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_CUSTOMER;
import static com.itonse.clotheslink.exception.ErrorCode.LOGIN_FAIL;

@RequiredArgsConstructor
@Service
public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public SignUpResponse signUp(SignUpDto dto) {

        customerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_CUSTOMER);
        });

        Customer customer = SignUpDto.toCustomerEntity(dto);

        customerRepository.save(customer);

        return SignUpResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public String signIn(SignInDto dto) {
        Customer customer = customerRepository.findByEmailAndPassword(
                dto.getEmail(), dto.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_FAIL));

        return jwtTokenProvider.createToken(
                customer.getEmail(), customer.getId(), UserType.CUSTOMER);
    }
}
