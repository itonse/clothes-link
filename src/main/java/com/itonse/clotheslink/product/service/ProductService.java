package com.itonse.clotheslink.product.service;

import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.dto.ProductDto;
import com.itonse.clotheslink.product.dto.ProductSummaryInfo;
import com.itonse.clotheslink.product.dto.UpdateProductDto;
import com.itonse.clotheslink.user.domain.Seller;

public interface ProductService {
    ProductSummaryInfo addProduct(String token, ProductDto dto);

    ProductSummaryInfo updateProduct(String token, Long productId, UpdateProductDto dto);

    Seller validateSeller(String token);

    Product validateUpdateProduct(String token, Long productId);
}
