package com.itonse.clotheslink.cart.service;

import com.itonse.clotheslink.cart.dto.CartDetail;

import java.util.List;

public interface CartSearchService {
    List<CartDetail> getCarts(String token, int page);
}
