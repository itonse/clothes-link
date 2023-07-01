package com.itonse.clotheslink.seller.service;

import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;

import com.itonse.clotheslink.seller.service.Impl.SellerAuthServiceImpl;
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
class SellerAuthServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private SellerAuthServiceImpl sellerAuthService;

    @Test
    void signUpSuccess() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("abc@naver.com")
                .password("12345678")
                .phone("010-1111-2222")
                .build();

        given(sellerRepository.findByEmail(dto.getEmail())).willReturn(Optional.empty());
        given(sellerRepository.save(any(Seller.class))).willReturn(new Seller());

        // when
        sellerAuthService.signUp(dto);

        // then
        verify(sellerRepository, times(1)).save(any(Seller.class));
    }

    @Test
    void signUpFail() {
        // given
        SignUpDto dto = SignUpDto.builder()
                .email("kkk@naver.com")
                .build();

        given(sellerRepository.findByEmail(dto.getEmail()))
                .willReturn(Optional.of(new Seller()));

        // when
        CustomException exception = assertThrows(CustomException.class, () -> {
            sellerAuthService.signUp(dto);
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

        given(sellerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword()))
                .willReturn(Optional.of(new Seller()));
        given(jwtTokenProvider.createToken(any(), any(), any()))
                .willReturn("sampleValidToken");

        // when
        String token = sellerAuthService.signin(dto);

        // then
        assertNotNull(token);
        assertEquals("sampleValidToken", token);
    }

    @Test
    void signinFail() {
        // given
        SignInDto dto = SignInDto.builder()
                .email("abc@naver.com")
                .password("11223344")
                .build();

        given(sellerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword()))
                .willThrow(new CustomException(LOGIN_FAIL));

        // when
        CustomException e = assertThrows(CustomException.class,() -> {
            sellerAuthService.signin(dto);
        });

        // then
        assertEquals(LOGIN_FAIL, e.getErrorCode());
        assertEquals("일치하는 회원정보가 없습니다." ,e.getMessage());

    }
}