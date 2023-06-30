package com.itonse.clotheslink.customer.controller;

import com.itonse.clotheslink.customer.dto.SignUpForm;
import com.itonse.clotheslink.customer.dto.SignUpResponse;
import com.itonse.clotheslink.customer.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/customer")
@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final SignService signService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpForm form) {

        return ResponseEntity.ok().body(signService.signUp(SignUpForm.toSignUpDto(form)));
    }
}
