package com.itonse.clotheslink.product.service;

import com.itonse.clotheslink.product.dto.ProductDetail;

import java.util.List;

public interface ProductSearchService {
    List<ProductDetail> getRecentByCategory(Long id, int page);

    List<ProductDetail> getRecentByName(String name, int page);

    ProductDetail getProductDetail(Long id);

    List<ProductDetail> getProductsByKeyword(String keyword, int page);
}
