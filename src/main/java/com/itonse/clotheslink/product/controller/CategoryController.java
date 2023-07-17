package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "새로운 카테고리 추가")
    @PostMapping
    public ResponseEntity<Map<String, String>> addCategory(
            @RequestParam String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", categoryService.addCategory(name));
        return ResponseEntity.ok().body(map);
    }
}
