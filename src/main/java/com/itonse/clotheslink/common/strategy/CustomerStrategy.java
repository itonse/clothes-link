package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.user.domain.Customer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    protected void modifyAuthStatus(String token) {
        Customer customer = tokenService.findCustomerByToken(token);
        customer.setAuthenticated(true);
    }
}
