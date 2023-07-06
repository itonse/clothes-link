package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.customer.domain.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerStrategy implements UserTypeStrategy {

    private final TokenService tokenService;

    @Override
    public boolean isAuthenticated(String token) {
        Customer customer = tokenService.findCustomerByToken(token);
        return customer.isAuthenticated();
    }
}
