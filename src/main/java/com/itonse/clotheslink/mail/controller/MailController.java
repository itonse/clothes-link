package com.itonse.clotheslink.mail.controller;

import com.itonse.clotheslink.mail.service.MailAuthService;
import com.itonse.clotheslink.common.UserVo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailAuthService mailAuthService;

    @ApiOperation(value = "본인인증 메일 전송")
    @PostMapping("/auth/mail")
    public ResponseEntity<UserVo> sendAuthMail(@RequestHeader(name = "Authorization") String token) {

        return ResponseEntity.ok().body(mailAuthService.processSendAuthMail(token));
    }

    @ApiOperation(value = "인증코드 확인", notes = "확인 후 인증 처리 한다.")
    @PatchMapping("user/auth-status")
    public ResponseEntity<UserVo> verifyCode(@RequestHeader(name = "Authorization") String token,
                                        @RequestParam("code") String authCode) {

        return ResponseEntity.ok().body(mailAuthService.confirmVerification(token, authCode));
    }
}
