package com.itonse.clotheslink.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddProductForm {

    @NotBlank(message = "카테고리 입력은 필수입니다.")
    private String category;

    @NotBlank(message = "상품명은 필수 항목입니다.")
    private String name;

    private String description;

    @NotNull(message = "상품 가격을 입력해주세요.")
    @Min(value = 100, message = "가격은 100원 이상이여야 합니다.")
    private int price;

    @NotNull(message = "재고수량 입력은 필수입니다.")
    @Min(value = 0, message = "재고수량은 0개 이상이여야 합니다.")
    private int stock;

    public static ProductDto toProductDto(AddProductForm form) {
        return ProductDto.builder()
                .category(form.getCategory())
                .name(form.getName())
                .description(form.getDescription())
                .price(form.getPrice())
                .stock(form.getStock())
                .build();
    }
}
