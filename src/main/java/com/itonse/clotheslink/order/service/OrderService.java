package com.itonse.clotheslink.order.service;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.order.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto.response order(String token, OrderDto.request request);

    void checkProductsAvailability(List<Cart> carts);

    int calculateTotalAmount(List<Cart> carts);
}
