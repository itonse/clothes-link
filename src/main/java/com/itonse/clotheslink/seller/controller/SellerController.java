package com.itonse.clotheslink.seller.controller;

import com.itonse.clotheslink.seller.dto.SignUpForm;
import com.itonse.clotheslink.seller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/seller")
@RequiredArgsConstructor
@RestController
public class SellerController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody @Valid SignUpForm form) {

        authenticationService.signUp(SignUpForm.toSignUpDto(form));

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
}
