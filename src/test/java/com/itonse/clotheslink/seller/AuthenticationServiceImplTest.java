package com.itonse.clotheslink.seller;

import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;

import com.itonse.clotheslink.seller.service.Impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.itonse.clotheslink.exception.ErrorCode.ALREADY_REGISTERED_SELLER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private SellerRepository sellerRepository;

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
        authenticationService.SignUp(dto);

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
                    authenticationService.SignUp(dto);
        });

        // then
        assertEquals(ALREADY_REGISTERED_SELLER, exception.getErrorCode());
        assertEquals("이미 가입된 이메일 입니다.", exception.getMessage());
    }
}