package com.itonse.clotheslink.order.controller;

import com.itonse.clotheslink.order.dto.OrderDto;
import com.itonse.clotheslink.order.dto.OrderForm;
import com.itonse.clotheslink.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/order")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto.response> orderCartItems(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody @Valid OrderForm form) {

        return ResponseEntity.ok()
                .body(orderService.order(token, OrderDto.request.from(form)));
    }
}
