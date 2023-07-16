package com.itonse.clotheslink.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CartInfo {

    private Long id;
    private int count;
    private Long productId;
    private Long customerId;
}
