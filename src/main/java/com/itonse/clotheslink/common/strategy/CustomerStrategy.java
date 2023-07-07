package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.customer.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerStrategy extends UserTypeStrategy {

    private final TokenService tokenService;

    public CustomerStrategy(TokenService tokenService) {
        super(UserType.CUSTOMER);
        this.tokenService = tokenService;
    }

    @Override
    protected boolean isAuthenticated(String token) {
        Customer customer = tokenService.findCustomerByToken(token);
        return customer.isAuthenticated();
    }
}
