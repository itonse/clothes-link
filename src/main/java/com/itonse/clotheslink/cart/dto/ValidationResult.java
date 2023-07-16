package com.itonse.clotheslink.cart.dto;

import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.user.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ValidationResult {

    private Product product;
    private Customer customer;
}
