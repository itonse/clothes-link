package com.itonse.clotheslink.admin.controller;

import com.itonse.clotheslink.admin.service.AdminService;
import com.itonse.clotheslink.admin.dto.TokenUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/token/validation")
    public ResponseEntity<TokenUserResponse> validateTokenTest(@RequestHeader(name = "AUTH-TOKEN") String token) {

        return ResponseEntity.ok().body(adminService.validateToken(token));
    }
}
