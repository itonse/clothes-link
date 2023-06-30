package com.itonse.clotheslink.customer.service.Impl;

import com.itonse.clotheslink.customer.domain.Customer;
import com.itonse.clotheslink.customer.dto.SignUpDto;
import com.itonse.clotheslink.customer.dto.SignUpResponse;
import com.itonse.clotheslink.customer.repository.CustomerRepository;
import com.itonse.clotheslink.customer.service.SignService;
import com.itonse.clotheslink.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_CUSTOMER;

@RequiredArgsConstructor
@Service
public class SignServiceImpl implements SignService {

    private final CustomerRepository customerRepository;

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
}
