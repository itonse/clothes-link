package com.itonse.clotheslink.product.service;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.dto.ProductDetail;
import com.itonse.clotheslink.product.dto.ProductDto;
import com.itonse.clotheslink.product.dto.ProductSummary;
import com.itonse.clotheslink.product.dto.UpdateProductDto;

import java.util.List;

public interface ProductManageService {
    ProductSummary addProduct(String token, ProductDto dto);

    ProductDetail updateProduct(String token, Long productId, UpdateProductDto dto);

    Product validateUpdateProduct(String token, Long productId);

    void updateProductStocksInCarts(List<Cart> carts);
}
