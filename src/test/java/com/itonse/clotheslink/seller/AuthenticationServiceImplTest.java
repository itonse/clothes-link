package com.itonse.clotheslink.seller;

import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;

import com.itonse.clotheslink.seller.service.Impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_SELLER;
import static com.itonse.clotheslink.exception.ErrorCode.LOGIN_FAIL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void signUpSuccess() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("abc@naver.com")
                .password("12345678")
                .phone("010-1111-2222")
                .build();

        given(sellerRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(sellerRepository.save(any(Seller.class))).willReturn(new Seller());

        // when
        authenticationService.signUp(dto);

        // then
        verify(sellerRepository, times(1)).save(any(Seller.class));
    }

    @Test
    void signUpFail() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("kkk@naver.com")
                .build();

        given(sellerRepository.findByEmail(anyString()))
                .willReturn(Optional.of(new Seller()));

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
                    authenticationService.signUp(dto);
        });

        // then
        assertEquals(ALREADY_REGISTERED_SELLER, exception.getErrorCode());
        assertEquals("이미 가입된 이메일 입니다.", exception.getMessage());
    }

    @Test
    void signInSuccess() {
        // given
        SignInDto dto = SignInDto.builder()
                .email("abc@naver.com")
                .password("11223344")
                .build();

        given(sellerRepository.findByEmailAndPassword(anyString(), anyString()))
                .willReturn(Optional.of(new Seller()));
        given(jwtTokenProvider.createToken(any(), any(), any()))
                .willReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWFAbmF2ZXIuY29tIiwianRpIjoiMSIsInJvbGVzIjoiU0VMTEVSIiwiaWF0IjoxNjg4MDg3NDM2LCJleHAiOjE2ODgwOTgyMzZ9.1q7TS9IRdY9ImwP9StI9NizYbD2LLcuAhw7ijnV8yiw");

        // when
        String token = authenticationService.signin(dto);

        // then
        assertNotNull(token);
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYWFAbmF2ZXIuY29tIiwianRpIjoiMSIsInJvbGVzIjoiU0VMTEVSIiwiaWF0IjoxNjg4MDg3NDM2LCJleHAiOjE2ODgwOTgyMzZ9.1q7TS9IRdY9ImwP9StI9NizYbD2LLcuAhw7ijnV8yiw"
                , token);
    }

    @Test
    void signinFail() {
        // given
        SignInDto dto = SignInDto.builder()
                .email("abc@naver.com")
                .password("11223344")
                .build();

        given(sellerRepository.findByEmailAndPassword(any(), any()))
                .willThrow(new CustomException(LOGIN_FAIL));

        // when
        CustomException e = assertThrows(CustomException.class,() -> {
            authenticationService.signin(dto);
        });

        // then
        assertEquals(LOGIN_FAIL, e.getErrorCode());
        assertEquals("일치하는 회원정보가 없습니다." ,e.getMessage());

    }
}