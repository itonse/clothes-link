package com.itonse.clotheslink.seller.service.Impl;

import com.itonse.clotheslink.admin.domain.Mail;
import com.itonse.clotheslink.admin.repository.MailRepository;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.seller.domain.Seller;
import com.itonse.clotheslink.seller.dto.SignInDto;
import com.itonse.clotheslink.seller.dto.UserInfoResponse;
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
    public UserInfoResponse signUp(SignUpDto dto) {

        Seller seller = SignUpDto.toSellerEntity(dto);

        sellerRepository.findByEmail(dto.getEmail()).ifPresent(e -> {
            throw new CustomException(ALREADY_REGISTERED_SELLER);
        });

        sellerRepository.save(seller);

        return UserInfoResponse.builder()
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
    public UserInfoResponse sendAuthMail(String token) {
        UserVo vo = jwtTokenProvider.getUserInfo(token);

        if (!vo.getUserType().equals(UserType.SELLER)) {
            throw new CustomException(MISMATCH_USER_INFO);
        }

        Seller seller = sellerRepository.findByEmail(vo.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (seller.isAuthenticated()) {
            throw new CustomException(ALREADY_VERIFIED);
        }

        String randomKey = RandomStringUtils.randomAlphanumeric(5);

        Optional<Mail> optionalMail = mailRepository.findByEmail(vo.getEmail());

        if (optionalMail.isPresent()) {
            Mail existMail = optionalMail.get();
            existMail.setAuthCode(randomKey);
            existMail.setValidUntil(LocalDateTime.now().plusMinutes(10));
        } else {
            Mail newMail = Mail.builder()
                    .email(vo.getEmail())
                    .authCode(randomKey)
                    .validUntil(LocalDateTime.now().plusMinutes(10))
                    .build();
            mailRepository.save(newMail);
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@e.clotheslink.com");
        mailMessage.setTo(vo.getEmail());
        mailMessage.setSubject("[From Clotheslink] 이메일 인증을 위한 인증코드가 발급되었습니다.");
        mailMessage.setText("아래의 인증코드를 복사하여 이메일 인증을 완료해주세요.\n"
                + "인증코드: " + randomKey + "\n"
                + "개인정보 보호를 위해 이메일 인증코드는 10분간 유효합니다.");

        javaMailSender.send(mailMessage);

        return UserInfoResponse.builder()
                .id(vo.getId())
                .email(vo.getEmail())
                .build();
    }

}
