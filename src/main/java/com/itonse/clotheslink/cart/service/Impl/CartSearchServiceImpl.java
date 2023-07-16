package com.itonse.clotheslink.cart.service.Impl;

import com.itonse.clotheslink.cart.domain.Cart;
import com.itonse.clotheslink.cart.dto.CartDetail;
import com.itonse.clotheslink.cart.dto.ConvertCartToDto;
import com.itonse.clotheslink.cart.repository.CartRepository;
import com.itonse.clotheslink.cart.service.CartSearchService;
import com.itonse.clotheslink.exception.CustomException;
import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.service.CustomerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.itonse.clotheslink.exception.ErrorCode.EMPTY_CART;

@RequiredArgsConstructor
@Service
public class CartSearchServiceImpl implements CartSearchService {

    private final CartRepository cartRepository;
    private final CustomerAuthService customerAuthService;

    private static final int PAGE_SIZE = 5;

    @Override
    public List<CartDetail> getCarts(String token, int page) {

        Customer customer = customerAuthService.validateCustomer(token);

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "modifiedAt"));

        List<Cart> carts =
                cartRepository.findCartsByCustomer(customer, pageable);

        if (carts.isEmpty()) {
            throw new CustomException(EMPTY_CART);
        }

        return carts.stream()
                .map(ConvertCartToDto::toCartDetail)
                .collect(Collectors.toList());
    }
}
