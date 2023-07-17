package com.itonse.clotheslink.user.controller;

import com.itonse.clotheslink.user.dto.SignInForm;
import com.itonse.clotheslink.user.dto.SignUpForm;
import com.itonse.clotheslink.user.dto.UserInfoResponse;
import com.itonse.clotheslink.user.service.CustomerAuthService;
import com.itonse.clotheslink.user.service.SellerAuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SignController {

    private final CustomerAuthService customerAuthService;
    private final SellerAuthService sellerAuthService;

    @ApiOperation(value = "판매자 회원가입")
    @PostMapping("/signup/seller")
    public ResponseEntity<UserInfoResponse> SignUpSeller(@RequestBody @Valid SignUpForm form) {

        return ResponseEntity.ok().body(sellerAuthService.signUp(SignUpForm.toSignUpDto(form)));
    }

    @ApiOperation(value = "고객 회원가입")
    @PostMapping("/signup/customer")
    public ResponseEntity<UserInfoResponse> SignUpCustomer(@RequestBody @Valid SignUpForm form) {

        return ResponseEntity.ok().body(customerAuthService.signUp(SignUpForm.toSignUpDto(form)));
    }

    @ApiOperation(value = "판매자 로그인")
    @PostMapping("/signin/seller")
    public ResponseEntity<Map<String, String>> signinSeller(@RequestBody @Valid SignInForm form) {

        String token = sellerAuthService.signin(SignInForm.toSignInDto(form));
        Map<String, String> response = new HashMap<>();
        response.put("TOKEN", token);

        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "고객 로그인")
    @PostMapping("/signin/customer")
    public ResponseEntity<Map<String, String>> signinCustomer(@RequestBody @Valid SignInForm form) {

        String token = customerAuthService.signIn(SignInForm.toSignInDto(form));
        Map<String, String> response = new HashMap<>();
        response.put("TOKEN", token);

        return ResponseEntity.ok().body(response);
    }
}
