package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.dto.ProductDetail;
import com.itonse.clotheslink.product.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RequiredArgsConstructor
@RestController
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @GetMapping("/category/{name}/latest")
    public ResponseEntity<List<ProductDetail>> getRecentByCategory (
                    @PathVariable("name") String name,
                    @RequestParam(value="page", defaultValue = "1") int page) {

        return ResponseEntity.ok()
                .body(productSearchService.getRecentByCategory(name, page));
    }
}
