package com.itonse.clotheslink.customer.controller;

import com.itonse.clotheslink.customer.dto.SignInForm;
import com.itonse.clotheslink.customer.dto.SignUpForm;
import com.itonse.clotheslink.customer.dto.UserInfoResponse;
import com.itonse.clotheslink.customer.service.CustomerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final CustomerAuthService customerAuthService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> signUp(@RequestBody @Valid SignUpForm form) {

        return ResponseEntity.ok()
                .body(customerAuthService.signUp(SignUpForm.toSignUpDto(form)));
    }

    @PostMapping("/signin/token")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody @Valid SignInForm form) {
        String token = customerAuthService.signIn(SignInForm.toSignInDto(form));
        Map<String, String> tokenResponse = new HashMap<>();
        tokenResponse.put("TOKEN", token);

        return ResponseEntity.ok().body(tokenResponse);
    }

}
