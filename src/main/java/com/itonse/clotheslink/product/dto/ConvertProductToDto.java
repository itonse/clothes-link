package com.itonse.clotheslink.product.dto;

import com.itonse.clotheslink.product.domain.Product;
import lombok.Builder;

@Builder
public class ConvertProductToDto {

    public static ProductDetail toProductDetail(Product product) {

        return ProductDetail.builder()
                .ProductId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
