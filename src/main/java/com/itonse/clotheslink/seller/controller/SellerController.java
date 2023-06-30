package com.itonse.clotheslink.seller.controller;

import com.itonse.clotheslink.seller.dto.SignInForm;
import com.itonse.clotheslink.seller.dto.SignUpResponse;
import com.itonse.clotheslink.seller.dto.SignUpForm;
import com.itonse.clotheslink.seller.dto.TokenUserResponse;
import com.itonse.clotheslink.seller.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/seller")
@RequiredArgsConstructor
@RestController
public class SellerController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpForm form) {

        return ResponseEntity.ok().body(authenticationService.signUp(SignUpForm.request(form)));
    }

    @PostMapping("/signin/token")
    public ResponseEntity<String> signin(@RequestBody @Valid SignInForm form) {

        return ResponseEntity.ok(authenticationService.signin(SignInForm.request(form)));
    }

    @GetMapping("/token/validation")
    public ResponseEntity<TokenUserResponse> validateTokenTest(@RequestHeader(name = "AUTH-TOKEN") String token) {

        return ResponseEntity.ok().body(authenticationService.validateToken(token));
    }
}
