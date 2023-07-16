package com.itonse.clotheslink.cart.service.Impl;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.cart.dto.CartDto;
import com.itonse.clotheslink.cart.dto.CartInfo;
import com.itonse.clotheslink.cart.dto.ValidationResult;
import com.itonse.clotheslink.cart.repository.CartRepository;
import com.itonse.clotheslink.cart.service.CartService;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.product.domain.Product;
import com.itonse.clotheslink.product.repository.ProductRepository;
import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.service.CustomerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.itonse.clotheslink.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CustomerAuthService customerAuthService;

    @Override
    @Transactional
    public CartInfo addNewProduct(String token, CartDto dto) {
        ValidationResult result = validateCustomerAndProduct(token, dto);
        Customer customer = result.getCustomer();
        Product product = result.getProduct();

        if (cartRepository.existsByProductAndCustomer(product, customer)) {
            throw new CustomException(ALREADY_EXISTS_CART);
        }
        checkProductQuantity(product, dto.getCount());

        Cart cart = Cart.builder()
                .count(dto.getCount())
                .product(product)
                .customer(customer)
                .build();

        cartRepository.save(cart);

        return CartInfo.builder()
                .id(cart.getId())
                .count(cart.getCount())
                .productId(product.getId())
                .customerId(customer.getId())
                .build();
    }

    @Override
    @Transactional
    public CartInfo modifyProductQuantity(String token, CartDto dto) {
        ValidationResult result = validateCustomerAndProduct(token, dto);
        Customer customer = result.getCustomer();
        Product product = result.getProduct();

        Cart cart = cartRepository.findByProductAndCustomer(product, customer)
                .orElseThrow(() -> new CustomException(NOT_EXISTS_CART));

        int totalCount = cart.getCount() + dto.getCount();
        checkProductQuantity(product, totalCount);
        cart.setCount(totalCount);

        return CartInfo.builder()
                .id(cart.getId())
                .count(cart.getCount())
                .productId(product.getId())
                .customerId(customer.getId())
                .build();
    }

    @Override
    public ValidationResult validateCustomerAndProduct(String token, CartDto dto) {
        Customer customer = customerAuthService.validateCustomer(token);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new CustomException(NOT_EXISTS_PRODUCT));

        if (product.isDeleted()) {
            throw new CustomException(SALES_STOPPED_PRODUCT);
        }

        return ValidationResult.builder()
                .customer(customer)
                .product(product)
                .build();
    }

    @Override
    public void checkProductQuantity(Product product, int count) {
        if (count < 1) {
            throw new CustomException(INVALID_QUANTITY);
        }
        if (product.getStock() < count) {
            throw new CustomException(EXCEEDED_STOCK_QUANTITY);
        }
    }
}
