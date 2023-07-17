package com.itonse.clotheslink.order.service.Impl;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.cart.dto.ConvertCart;
import com.itonse.clotheslink.cart.service.CartManageService;
import com.itonse.clotheslink.cart.service.CartSearchService;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.order.domain.Order;
import com.itonse.clotheslink.order.dto.OrderDto;
import com.itonse.clotheslink.order.repository.OrderRepository;
import com.itonse.clotheslink.order.service.OrderService;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.service.ProductManageService;
import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.service.CustomerAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.itonse.clotheslink.exception.ErrorCode.INVALID_CART_PRODUCT;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerAuthService customerAuthService;
    private final CartSearchService cartSearchService;
    private final CartManageService cartManageService;
    private final ProductManageService productManageService;

    @Override
    @Transactional
    public OrderDto.response order(String token, OrderDto.request request) {
        Customer customer = customerAuthService.validateCustomer(token);
        List<Cart> carts = cartSearchService.getCustomerCarts(customer);

        checkProductsAvailability(carts);

        int totalAmount = calculateTotalAmount(carts);

        productManageService.updateProductStocksInCarts(carts);

        cartManageService.refreshCustomerCarts(customer);

        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .address(request.getAddress())
                .phone(customer.getPhone())
                .requests(request.getRequests())
                .amount(totalAmount)
                .orderDateTime(LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .customer(customer)
                .build();

        orderRepository.save(order);

        return OrderDto.response.builder()
                .orderId(order.getId())
                .customerId(customer.getId())
                .amount(order.getAmount())
                .orderDateTime(order.getOrderDateTime())
                .build();
    }

    /**
     * 고객의 장바구니의 모든 상품의 수량과 판매중지 여부를 체크합니다.
     * 만약 상품의 수량이 재고보다 많거나, 상품이 판매 중지된 경우 CustomException 을 발생시킵니다.
     *
     * @param carts 검사할 고객의 장바구니
     */
    @Override
    public void checkProductsAvailability(List<Cart> carts) {
        boolean hasError = carts.stream().anyMatch(cart -> {
            Product product = cart.getProduct();

            if (cart.getCount() > product.getStock()) {
                log.warn("Cart (id: " + cart.getId() + ") 의 " + product.getName() + " 수량이 부족합니다.");
                return true;
            }
            if (product.isDeleted()) {
                log.warn("Cart (id: " + cart.getId() + ") 의 " + product.getName() + " 은 판매 중지된 상품입니다.");
                return true;
            }

            return false;
        });

        if (hasError) {
            throw new CustomException(INVALID_CART_PRODUCT);
        }
    }

    @Override
    public int calculateTotalAmount(List<Cart> carts) {
        return carts.stream()
                .mapToInt(cart -> ConvertCart.toCartDetail(cart).getTotalAmount()).sum();
    }
}
