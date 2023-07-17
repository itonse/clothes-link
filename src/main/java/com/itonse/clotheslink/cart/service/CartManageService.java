package com.itonse.clotheslink.cart.service;

import com.itonse.clotheslink.cart.dto.CartDto;
import com.itonse.clotheslink.cart.dto.CartInfo;
import com.itonse.clotheslink.cart.dto.ValidationResult;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.user.domain.Customer;

public interface CartManageService {
    CartInfo addNewProduct(String token, CartDto dto);

    CartInfo modifyProductQuantity(String token, CartDto dto);

    Long deleteProduct(String token, Long id);

    ValidationResult validateCustomerAndProduct(String token, CartDto dto);

    void checkProductQuantity(Product product, int count);

    void refreshCustomerCarts(Customer customer);
}
