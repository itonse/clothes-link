package com.itonse.clotheslink.product.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductDetail {

    private Long productId;
    private String name;
    private String description;
    private int price;
    private int stock;
    private boolean deleted;
}
