package com.itonse.clotheslink.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class request {
        private String customerName;
        private String address;
        private String requests;

        public static OrderDto.request from(OrderForm form) {
            return request.builder()
                    .customerName(form.getCustomerName())
                    .address(form.getAddress())
                    .requests(form.getRequests())
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class response {
        private Long orderId;
        private Long customerId;
        private int amount;
        private String orderDateTime;
    }
}
