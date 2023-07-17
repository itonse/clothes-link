package com.itonse.clotheslink.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderForm {

    @NotBlank(message = "주문자명 입력은 필수입니다.")
    private String customerName;
    @NotBlank(message = "배송지 입력은 필수입니다.")
    private String address;
    private String requests;
}
