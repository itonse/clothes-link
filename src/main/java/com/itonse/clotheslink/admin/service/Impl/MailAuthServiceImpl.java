package com.itonse.clotheslink.admin.service.Impl;

import com.itonse.clotheslink.admin.domain.Mail;
import com.itonse.clotheslink.admin.repository.MailRepository;
import com.itonse.clotheslink.admin.service.MailAuthService;
import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.common.*;
import com.itonse.clotheslink.common.strategy.MailAuthContext;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.exception.CustomException;
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
public class MailAuthServiceImpl implements MailAuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MailRepository mailRepository;
    private final TokenService tokenService;
    private final JavaMailSender javaMailSender;

    private final MailAuthContext mailAuthContext;

    @Override
    public boolean verifyUserAuth(UserType userType, String token) {

        return mailAuthContext.isAuthenticated(userType, token);
    }

    @Override
    public UserVo verifyEmailSending(String token) {
        tokenService.validateToken(token);

        UserVo vo = jwtTokenProvider.getUserInfo(token);

        if (verifyUserAuth(vo.getUserType(), token)) {
            throw new CustomException(ALREADY_VERIFIED);
        }

        return vo;
    }

    @Override
    @Transactional
    public Mail manageMail(UserVo vo) {

        String randomKey = RandomStringUtils.randomAlphanumeric(5);

        // 유저 정보에 해당하는 Mail 객체 찾기
        Optional<Mail> optionalMail
                = mailRepository.findByEmailAndUserType(vo.getEmail(), vo.getUserType());

        if (optionalMail.isPresent()) {        // Mail 객체가 존재한다면, 객체 정보를 업데이트
            Mail existMail = optionalMail.get();
            existMail.setAuthCode(randomKey);
            existMail.setExpiredAt(LocalDateTime.now().plusMinutes(30));
            return existMail;
        } else {        // 존재하지 않는다면 새 Mail 객체를 생성하여 저장
            Mail newMail = Mail.builder()
                    .userType(vo.getUserType())
                    .authCode(randomKey)
                    .email(vo.getEmail())
                    .expiredAt(LocalDateTime.now().plusMinutes(30))
                    .build();
            mailRepository.save(newMail);
            return newMail;
        }
    }

    @Override
    public UserVo sendMail(UserVo vo, Mail mail) {
        // 인증메일 내용 작성
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(vo.getEmail());
        mailMessage.setSubject("[From Clotheslink] 이메일 인증을 위한 인증코드가 발급되었습니다.");
        mailMessage.setText("아래의 인증코드를 복사하여 이메일 인증을 완료해주세요.\n"
                + "인증코드: " + mail.getAuthCode() + "\n"
                + "개인정보 보호를 위해 이메일 인증코드는 30분간 유효합니다.");

        javaMailSender.send(mailMessage);

        return vo;
    }

    @Override
    @Transactional
    public UserVo processSendAuthMail(String token) {
        UserVo vo = verifyEmailSending(token);
        Mail mail = manageMail(vo);
        return sendMail(vo, mail);
    }

    @Override
    @Transactional
    public UserVo confirmVerification(String token, String authCode) {
        tokenService.validateToken(token);

        UserVo vo = jwtTokenProvider.getUserInfo(token);

        if (verifyUserAuth(vo.getUserType(), token)) {
            throw new CustomException(ALREADY_VERIFIED);
        }

        Mail mail = mailRepository
                .findByEmailAndUserType(vo.getEmail(), vo.getUserType())
                .orElseThrow(() -> new CustomException(INITIATE_EMAIL_REQUEST));

        verifyCode(mail, authCode);

        mailRepository.delete(mail);

        mailAuthContext.modifyAuthStatus(vo.getUserType(), token);

        return vo;
    }

    public void verifyCode(Mail mail, String authCode) {
        if (mail.getExpiredAt().isBefore(LocalDateTime.now())) {
            mailRepository.delete(mail);
            throw new CustomException(EXPIRED_AUTH_CODE);
        }

        if(!authCode.equals(mail.getAuthCode())) {
            throw new CustomException(INVALID_AUTH_CODE);
        }
    }

}
