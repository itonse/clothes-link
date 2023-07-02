package com.itonse.clotheslink.seller.service.Impl;

import com.itonse.clotheslink.admin.domain.Mail;
import com.itonse.clotheslink.admin.repository.MailRepository;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SendMailDto;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.SignUpResponse;
import com.itonse.clotheslink.seller.dto.SignUpDto;
import com.itonse.clotheslink.seller.repository.SellerRepository;
import com.itonse.clotheslink.seller.service.SellerAuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class SellerAuthServiceImpl implements SellerAuthService {

    private final SellerRepository sellerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;

    @Override
    public SignUpResponse signUp(SignUpDto dto) {

        Seller seller = SignUpDto.toSellerEntity(dto);

        sellerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        });

        sellerRepository.save(seller);

        return SignUpResponse.builder()
                .id(seller.getId())
                .email(seller.getEmail())
                .build();
    }

    @Override
    public String signin(SignInDto dto) {
        Seller seller = sellerRepository.findByEmailAndPassword(
                dto.getEmail(), dto.getPassword())
                        .orElseThrow(() -> new CustomException(LOGIN_FAIL));

        return jwtTokenProvider.createToken(seller.getEmail(), seller.getId(), UserType.SELLER);
    }

    @Override
    @Transactional
    public SendMailDto.Response sendAuthMail(SendMailDto.Request request) {
        Seller seller = sellerRepository
                .findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (seller.isAuthenticated()) {
            throw new CustomException(ALREADY_VERIFIED);
        }

        String randomKey = RandomStringUtils.randomAlphanumeric(5);

        Optional<Mail> optionalMail = mailRepository.findByEmail(request.getEmail());

        if (optionalMail.isPresent()) {
            Mail existMail = optionalMail.get();
            existMail.setAuthCode(randomKey);
            existMail.setValidUntil(LocalDateTime.now().plusMinutes(10));
        } else {
            Mail newMail = Mail.builder()
                    .email(request.getEmail())
                    .authCode(randomKey)
                    .verified(false)
                    .validUntil(LocalDateTime.now().plusMinutes(10))
                    .build();
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@e.clotheslink.com");
        mailMessage.setTo(request.getEmail());
        mailMessage.setSubject("[From Clotheslink] 이메일 인증을 위한 인증코드가 발급되었습니다.");
        mailMessage.setText("아래의 인증코드를 복사하여 이메일 인증을 완료해주세요.\n"
                + "인증코드: " + randomKey + "\n"
                + "개인정보 보호를 위해 이메일 인증코드는 10분간 유효합니다.");

        javaMailSender.send(mailMessage);

        return SendMailDto.Response.builder()
                .email(request.getEmail())
                .build();
    }

}
