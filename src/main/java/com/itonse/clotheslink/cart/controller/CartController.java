package com.itonse.clotheslink.cart.controller;

import com.itonse.clotheslink.cart.dto.CartForm;
import com.itonse.clotheslink.cart.dto.CartInfo;
import com.itonse.clotheslink.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("product/new")
    public ResponseEntity<CartInfo> addNewProduct(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody @Valid CartForm form) {

        return ResponseEntity.ok()
                .body(cartService.addNewProduct(token, CartForm.toCartDto(form)));
    }
}
