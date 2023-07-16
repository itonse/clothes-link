package com.itonse.clotheslink.cart.controller;

import com.itonse.clotheslink.cart.dto.CartForm;
import com.itonse.clotheslink.cart.dto.CartInfo;
import com.itonse.clotheslink.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping("/product/new")
    public ResponseEntity<CartInfo> addNewProduct(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody @Valid CartForm form) {

        return ResponseEntity.ok()
                .body(cartService.addNewProduct(token, CartForm.toCartDto(form)));
    }

    @PatchMapping("/product/count")
    public ResponseEntity<CartInfo> modifyProductQuantity(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody @Valid CartForm form) {

        return ResponseEntity.ok()
                .body(cartService.modifyProductQuantity(token, CartForm.toCartDto(form)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> deleteProduct(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable Long id) {

        Map<String, Long> map = new HashMap<>();
        map.put("cartId", cartService.deleteProduct(token, id));
        return ResponseEntity.ok()
                .body(map);
    }
}
