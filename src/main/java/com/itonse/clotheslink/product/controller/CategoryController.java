package com.itonse.clotheslink.product.controller;

import com.itonse.clotheslink.product.service.CategoryService;
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

    @PostMapping
    public ResponseEntity<Map<String, String>> addCategory(
            @RequestParam String name) {
        Map<String, String> map = new HashMap<>();
        map.put("name", categoryService.addCategory(name));
        return ResponseEntity.ok().body(map);
    }
}
