package com.itonse.clotheslink.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UpdateProductDto {

    private String name;
    private String description;
    private String price;
    private String stock;
    private boolean deleted;
}
