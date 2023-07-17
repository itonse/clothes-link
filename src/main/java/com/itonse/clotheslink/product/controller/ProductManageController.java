package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.dto.AddProductForm;
import com.itonse.clotheslink.product.dto.ProductDetail;
import com.itonse.clotheslink.product.dto.ProductSummary;
import com.itonse.clotheslink.product.dto.UpdateProductForm;
import com.itonse.clotheslink.product.service.ProductManageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class ProductManageController {

    private final ProductManageService productManageService;

    @ApiOperation(value = "상품 등록")
    @PostMapping
    public ResponseEntity<ProductSummary> addProduct(@RequestHeader(name = "Authorization") String token,
                                                     @RequestBody @Valid AddProductForm form) {

        return ResponseEntity.ok()
                .body(productManageService.addProduct(token, AddProductForm.toProductDto(form)));
    }

    @ApiOperation(value = "상품정보 업데이트")
    @PutMapping
    public ResponseEntity<ProductDetail> updateProduct(@RequestHeader(name = "Authorization") String token,
                                                       @RequestParam Long id,
                                                       @RequestBody @Valid UpdateProductForm form) {
        return ResponseEntity.ok()
                .body(productManageService.updateProduct(token, id, UpdateProductForm.toDto(form)));
    }
}
