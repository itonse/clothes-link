package com.itonse.clotheslink.product.dto;

import com.itonse.clotheslink.product.domain.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ProductDto {

    private String category;
    private String name;
    private String description;
    private String price;
    private String stock;

    public static Product toEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
    }
}
