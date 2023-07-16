package com.itonse.clotheslink.cart.service;

import com.itonse.clotheslink.cart.dto.CartDto;
import com.itonse.clotheslink.cart.dto.CartInfo;
import com.itonse.clotheslink.cart.dto.ValidationResult;
import com.itonse.clotheslink.product.domain.Product;

public interface CartService {
    CartInfo addNewProduct(String token, CartDto dto);

    CartInfo modifyProductQuantity(String token, CartDto dto);

    ValidationResult validateCustomerAndProduct(String token, CartDto dto);

    void checkProductQuantity(Product product, int count);
}
