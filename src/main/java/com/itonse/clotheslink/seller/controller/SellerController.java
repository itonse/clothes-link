package com.itonse.clotheslink.seller.controller;

import com.itonse.clotheslink.seller.dto.SignInForm;
import com.itonse.clotheslink.seller.dto.UserInfoResponse;
import com.itonse.clotheslink.seller.dto.SignUpForm;
import com.itonse.clotheslink.seller.service.SellerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/seller")
@RequiredArgsConstructor
@RestController
public class SellerController {
    private final SellerAuthService sellerAuthService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUp(@RequestBody @Valid SignUpForm form) {

        return ResponseEntity.ok().body(sellerAuthService.signUp(SignUpForm.toSignUpDto(form)));
    }

    @PostMapping("/signin/token")
    public ResponseEntity<Map> signin(@RequestBody @Valid SignInForm form) {

        String token = sellerAuthService.signin(SignInForm.toSignInDto(form));
        Map<String, String> response = new HashMap<>();
        response.put("TOKEN", token);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/send/email")
    public ResponseEntity<UserInfoResponse> sendAuthEmail(@RequestHeader(name = "Authorization") String token) {

        return ResponseEntity.ok().body(sellerAuthService.sendAuthMail(token));
    }
}
