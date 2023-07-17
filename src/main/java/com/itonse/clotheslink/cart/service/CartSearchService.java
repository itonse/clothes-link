package com.itonse.clotheslink.cart.service;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.cart.dto.CartDetail;
import com.itonse.clotheslink.user.domain.Customer;

import java.util.List;

public interface CartSearchService {
    List<CartDetail> getCarts(String token, int page);

    List<Cart> getCustomerCarts(Customer customer);
}
