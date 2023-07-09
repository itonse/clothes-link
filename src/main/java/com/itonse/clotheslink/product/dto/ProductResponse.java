package com.itonse.clotheslink.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductResponse {
    private Long categoryId;
    private Long productId;
    private Long sellerId;
    private String productName;
}
