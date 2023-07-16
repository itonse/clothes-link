package com.itonse.clotheslink.cart.dto;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.product.domain.Product;
import lombok.*;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ConvertCartToDto {
    private Long cartId;
    private String productName;
    private Long count;
    private int totalAmount;
    private boolean deletedProduct;
    private String lastModifiedAt;

    public static CartDetail toCartDetail(Cart cart) {
        Product product = cart.getProduct();

        return CartDetail.builder()
                .cartId(cart.getId())
                .productName(product.getName())
                .count(cart.getCount())
                .totalAmount(product.getPrice() * cart.getCount())
                .deletedProduct(product.isDeleted())
                .lastModifiedAt(cart.getModifiedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
