package com.itonse.clotheslink.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CartDetail {

    private Long cartId;
    private String productName;
    private int count;
    private boolean deletedProduct;
    private String lastModifiedAt;
}
