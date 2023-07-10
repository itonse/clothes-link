package com.itonse.clotheslink.product.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UpdateProductForm {

    @NotBlank(message = "상품명 입력은 필수입니다.")
    private String name;

    @NotBlank(message = "상품설명 입력은 필수입니다.(공백 가능)")
    private String description;

    @NotNull(message = "가격 입력은 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상이여야 합니다.")
    private String price;

    @NotNull(message = "재고수량 입력은 필수입니다.")
    @Min(value = 0, message = "재고수량은 0개 이상이여야 합니다.")
    private String stock;

    private boolean deleted;

    public static UpdateProductDto toDto(UpdateProductForm form) {
        return UpdateProductDto.builder()
                .name(form.getName())
                .description(form.getDescription())
                .price(form.getPrice())
                .stock(form.getStock())
                .deleted(form.isDeleted())
                .build();
    }
}
