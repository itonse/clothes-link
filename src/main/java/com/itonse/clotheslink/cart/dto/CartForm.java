package com.itonse.clotheslink.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CartForm {

    @NotNull(message = "상품번호를 입력해주세요.")
    private Long productId;

    @NotNull(message = "수량을 입력해 주세요.")
    private int count;

    public static CartDto toCartDto(CartForm form) {
        return CartDto.builder()
                .productId(form.getProductId())
                .count(form.getCount())
                .build();
    }
}
