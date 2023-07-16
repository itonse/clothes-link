package com.itonse.clotheslink.cart.dto;

import com.itonse.clotheslink.cart.domain.Cart;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public class ConvertCartToDto {

    public static CartDetail toCartDetail(Cart cart) {
        String lastModifiedAt = cart.getModifiedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return CartDetail.builder()
                .cartId(cart.getId())
                .productName(cart.getProduct().getName())
                .count(cart.getCount())
                .deletedProduct(cart.getProduct().isDeleted())
                .lastModifiedAt(lastModifiedAt)
                .build();
    }
}
