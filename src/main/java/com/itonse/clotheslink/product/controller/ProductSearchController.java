package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.dto.ProductDetail;
import com.itonse.clotheslink.product.service.ProductSearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    @ApiOperation(value = "카테고리 별 상품 조회", notes = "최신순으로 조회한다.")
    @GetMapping("/categories/{id}/products/latest")
    public ResponseEntity<List<ProductDetail>> getRecentByCategory (
            @PathVariable("id") Long id,
            @RequestParam(value="page", defaultValue = "1") int page) {

        return ResponseEntity.ok()
                .body(productSearchService.getRecentByCategory(id, page));
    }

    @ApiOperation(value = "상품명으로 상품 검색", notes = "최신순으로 조회한다.")
    @GetMapping("products/latest")
    public ResponseEntity<List<ProductDetail>> getRecentByName(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return ResponseEntity.ok()
                .body(productSearchService.getRecentByName(name, page));
    }

    @ApiOperation(value = "상품 id로 상품 검색")
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDetail> getProductById(@PathVariable("id") Long id) {

        return ResponseEntity.ok()
                .body(productSearchService.getProductDetail(id));
    }

    @ApiOperation(value = "키워드로 관련 상품 검색", notes = "상품명과 상품설명에서 키워드를 검색한다.")
    @GetMapping("products/search")
    public ResponseEntity<List<ProductDetail>> getProductsByKeyword(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        return ResponseEntity.ok()
                .body(productSearchService.getProductsByKeyword(keyword, page));
    }
}
