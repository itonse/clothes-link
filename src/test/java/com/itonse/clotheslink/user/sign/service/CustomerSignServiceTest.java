package com.itonse.clotheslink.user.sign.service;

import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.dto.SignInDto;
import com.itonse.clotheslink.user.dto.SignUpDto;
import com.itonse.clotheslink.user.repository.CustomerRepository;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.user.service.Impl.CustomerAuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_CUSTOMER;
import static com.itonse.clotheslink.exception.ErrorCode.LOGIN_FAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerSignServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    CustomerAuthServiceImpl customerAuthService;

    @Test
    void signUpSuccess() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("bbb@naver.com")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when
        customerAuthService.signUp(dto);

        // then
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void signUpFail() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("bbb@naver.com")
                .password("11223344")
                .phone("010-1111-2222")
                .build();

        given(customerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new Customer()));

        // when
        CustomException e = assertThrows(CustomException.class,
                () -> customerAuthService.signUp(dto));

        // then
        assertEquals(ALREADY_REGISTERED_CUSTOMER, e.getErrorCode());
        assertEquals("이미 가입된 이메일 입니다.", e.getMessage());
        verify(customerRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void signInSuccess() {
        //given
        SignInDto dto = SignInDto.builder()
                .email("bbb@naver.com")
                .password("11223344")
                .build();

        given(customerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword()))
                .willReturn(Optional.of(new Customer()));
        given(jwtTokenProvider.createToken(any(), any(), any()))
                .willReturn("sampleValidToken");
        // when
        String token = customerAuthService.signIn(dto);

        // then
        assertEquals("sampleValidToken", token);
    }

    @Test
    void signInFail() {
        // given
        SignInDto dto = SignInDto.builder()
                .email("bbb@naver.com")
                .password("11223344")
                .build();

        given(customerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword()))
                .willThrow(new CustomException(LOGIN_FAIL));

        // when
        CustomException e = assertThrows(CustomException.class,
                () -> customerAuthService.signIn(dto));

        // then
        assertEquals(LOGIN_FAIL, e.getErrorCode());
        assertEquals("일치하는 회원정보가 없습니다.", e.getMessage());
    }
}