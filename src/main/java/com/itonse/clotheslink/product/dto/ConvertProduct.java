package com.itonse.clotheslink.product.dto;

import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.domain.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ConvertProduct {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class toProductDetail {
        private Long productId;
        private String name;
        private String description;
        private int price;
        private int stock;
        private boolean deleted;

        public static ProductDetail from(Product product) {
            return ProductDetail.builder()
                    .productId(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .deleted(product.isDeleted())
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class toProductSummary {
        private Long categoryId;
        private Long productId;
        private Long sellerId;
        private String productName;

        public static ProductSummary from(Product product) {
            return ProductSummary.builder()
                    .categoryId(product.getCategory().getId())
                    .productId(product.getId())
                    .sellerId(product.getSeller().getId())
                    .productName(product.getName())
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class toProductDocument {
        private Long id;
        private String name;
        private String description;
        private int price;
        private int stock;
        private boolean deleted;

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
    }
}
