package com.itonse.clotheslink.mail.controller;

import com.itonse.clotheslink.mail.service.MailAuthService;
import com.itonse.clotheslink.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MailController {

    private final MailAuthService mailAuthService;

    @PostMapping("/auth/mail")
    public ResponseEntity<UserVo> sendAuthMail(@RequestHeader(name = "Authorization") String token) {

        return ResponseEntity.ok().body(mailAuthService.processSendAuthMail(token));
    }

    @PatchMapping("user/auth-status")
    public ResponseEntity<UserVo> verifyCode(@RequestHeader(name = "Authorization") String token,
                                        @RequestParam("code") String authCode) {

        return ResponseEntity.ok().body(mailAuthService.confirmVerification(token, authCode));
    }
}
