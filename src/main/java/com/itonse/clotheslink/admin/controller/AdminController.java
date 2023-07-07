package com.itonse.clotheslink.admin.controller;

import com.itonse.clotheslink.admin.service.MailAuthService;
import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.common.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AdminController {

    private final TokenService tokenService;
    private final MailAuthService mailAuthService;

    @GetMapping("/token/validation")
    public ResponseEntity<UserVo> validateTokenTest(@RequestHeader(name = "Authorization") String token) {

        return ResponseEntity.ok().body(tokenService.validateToken(token));
    }

    @PostMapping("/auth/mail")
    public ResponseEntity<UserVo> sendAuthMail(@RequestHeader(name = "Authorization") String token) {

        return ResponseEntity.ok().body(mailAuthService.processSendAuthMail(token));
    }

    @PatchMapping("user/auth-status")
    public ResponseEntity<?> verifyCode(@RequestHeader(name = "Authorization") String token,
                                        @RequestParam("code") String authCode) {

        return ResponseEntity.ok().body(mailAuthService.confirmVerification(token, authCode));
    }
}
