package com.itonse.clotheslink.product.dto;

import com.itonse.clotheslink.product.domain.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductDocumentDto {
    private Long productId;
    private String name;
    private String description;
    private int price;
    private int stock;
    private boolean deleted;

    public static ProductDetail toProductDetail(ProductDocument productDocument) {
        return ProductDetail.builder()
                .productId(productDocument.getId())
                .name(productDocument.getName())
                .description(productDocument.getDescription())
                .price(productDocument.getPrice())
                .stock(productDocument.getStock())
                .deleted(productDocument.isDeleted())
                .build();
    }
}
