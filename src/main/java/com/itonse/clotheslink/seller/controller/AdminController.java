package com.itonse.clotheslink.seller.controller;

import com.itonse.clotheslink.common.UserVo;
import com.itonse.clotheslink.config.security.JwtTokenProvider;
import com.itonse.clotheslink.seller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("token")
    public ResponseEntity<?> validateTokenTest(@RequestHeader(name = "AUTH-TOKEN") String token) {
        try {
            UserVo vo = jwtTokenProvider.getUserInfo(token);
            return ResponseEntity.ok().body(authenticationService.findTokenUser(vo.getId(), vo.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("토큰이 만료되었거나 존재하지 않습니다.");
        }
    }

}
