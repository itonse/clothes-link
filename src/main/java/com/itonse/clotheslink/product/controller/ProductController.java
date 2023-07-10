package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.dto.AddProductForm;
import com.itonse.clotheslink.product.dto.ProductResponse;
import com.itonse.clotheslink.product.dto.UpdateProductForm;
import com.itonse.clotheslink.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody @Valid AddProductForm form) {

        return ResponseEntity.ok()
                .body(productService.addProduct(token, AddProductForm.toProductDto(form)));
    }

    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@RequestHeader(name = "Authorization") String token,
                                                      @RequestParam Long id,
                                                      @RequestBody @Valid UpdateProductForm form) {
        return ResponseEntity.ok()
                .body(productService.updateProduct(token, id, UpdateProductForm.toDto(form)));
    }
}
