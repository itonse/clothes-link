package com.itonse.clotheslink.cart.controller;

import com.itonse.clotheslink.cart.dto.CartDetail;
import com.itonse.clotheslink.cart.dto.CartForm;
import com.itonse.clotheslink.cart.dto.CartInfo;
import com.itonse.clotheslink.cart.service.CartManageService;
import com.itonse.clotheslink.cart.service.CartSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartManageService cartManageService;
    private final CartSearchService cartSearchService;

    @PostMapping("/product/new")
    public ResponseEntity<CartInfo> addNewProduct(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody @Valid CartForm form) {

        return ResponseEntity.ok()
                .body(cartManageService.addNewProduct(token, CartForm.toCartDto(form)));
    }

    @PatchMapping("/product/count")
    public ResponseEntity<CartInfo> modifyProductQuantity(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody @Valid CartForm form) {

        return ResponseEntity.ok()
                .body(cartManageService.modifyProductQuantity(token, CartForm.toCartDto(form)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> deleteProduct(
            @RequestHeader(name = "Authorization") String token,
            @PathVariable Long id) {

        Map<String, Long> map = new HashMap<>();
        map.put("cartId", cartManageService.deleteProduct(token, id));
        return ResponseEntity.ok()
                .body(map);
    }

    @GetMapping("/customer/recently-update")
    public ResponseEntity<List<CartDetail>> getCartsByCustomer(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam(value="page", defaultValue = "1") int page) {

        return ResponseEntity.ok()
                .body(cartSearchService.getCarts(token, page));
    }
}
