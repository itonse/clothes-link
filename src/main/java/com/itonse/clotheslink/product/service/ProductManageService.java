package com.itonse.clotheslink.product.service;

import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.dto.ProductDto;
import com.itonse.clotheslink.product.dto.ProductSummary;
import com.itonse.clotheslink.product.dto.UpdateProductDto;

public interface ProductManageService {
    ProductSummary addProduct(String token, ProductDto dto);

    ProductSummary updateProduct(String token, Long productId, UpdateProductDto dto);

    Product validateUpdateProduct(String token, Long productId);
}
