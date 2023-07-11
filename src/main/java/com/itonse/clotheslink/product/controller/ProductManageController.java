package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.dto.AddProductForm;
import com.itonse.clotheslink.product.dto.ProductSummaryInfo;
import com.itonse.clotheslink.product.dto.UpdateProductForm;
import com.itonse.clotheslink.product.service.ProductManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class ProductManageController {

    private final ProductManageService productManageService;

    @PostMapping
    public ResponseEntity<ProductSummaryInfo> addProduct(@RequestHeader(name = "Authorization") String token,
                                                         @RequestBody @Valid AddProductForm form) {

        return ResponseEntity.ok()
                .body(productManageService.addProduct(token, AddProductForm.toProductDto(form)));
    }

    @PutMapping
    public ResponseEntity<ProductSummaryInfo> updateProduct(@RequestHeader(name = "Authorization") String token,
                                                            @RequestParam Long id,
                                                            @RequestBody @Valid UpdateProductForm form) {
        return ResponseEntity.ok()
                .body(productManageService.updateProduct(token, id, UpdateProductForm.toDto(form)));
    }
}
