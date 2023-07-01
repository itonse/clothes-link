package com.itonse.clotheslink.customer.service.Impl;

import com.itonse.clotheslink.customer.domain.Customer;
import com.itonse.clotheslink.customer.dto.SignUpDto;
import com.itonse.clotheslink.customer.repository.CustomerRepository;
import com.itonse.clotheslink.exception.CustomException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_CUSTOMER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    SignServiceImpl signService;

    @Test
    void signUpSuccess() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("bbb@naver.com")
                .name("김일번")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when
        signService.signUp(dto);

        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void signUpFail() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("bbb@naver.com")
                .name("김일번")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new Customer()));

        // when
        CustomException e = assertThrows(CustomException.class, () -> {
            signService.signUp(dto);
        });

        // then
        assertEquals(ALREADY_REGISTERED_CUSTOMER, e.getErrorCode());
        assertEquals("이미 가입된 이메일 입니다.", e.getMessage());
        verify(customerRepository, times(0)).save(any(Customer.class));
    }
}