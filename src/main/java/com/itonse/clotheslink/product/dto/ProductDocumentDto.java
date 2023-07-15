package com.itonse.clotheslink.product.dto;

import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.domain.ProductDocument;

public class ProductDocumentDto {

    public static ProductDocument from (Product product) {
        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .deleted(product.isDeleted())
                .build();
    }

    public static ProductDetail toProductDetail(ProductDocument productDocument) {
        return ProductDetail.builder()
                .ProductId(productDocument.getId())
                .name(productDocument.getName())
                .description(productDocument.getDescription())
                .price(productDocument.getPrice())
                .stock(productDocument.getStock())
                .deleted(productDocument.isDeleted())
                .build();
    }
}
