package com.itonse.clotheslink.product.service;

import com.itonse.clotheslink.product.dto.ProductDetail;

import java.util.List;

public interface ProductSearchService {
    List<ProductDetail> getRecentByCategory(Long id, int page);
}
