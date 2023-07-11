package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.user.domain.Customer;
import com.itonse.clotheslink.user.service.CustomerAuthService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CustomerStrategy extends UserTypeStrategy {

    private final CustomerAuthService customerAuthService;

    public CustomerStrategy(CustomerAuthService customerAuthService) {
        super(UserType.CUSTOMER);
        this.customerAuthService = customerAuthService;
    }

    @Override
    protected boolean isAuthenticated(String token) {
        Customer customer = customerAuthService.findCustomerByToken(token);
        return customer.isAuthenticated();
    }

    @Override
    @Transactional
    protected void modifyAuthStatus(String token) {
        Customer customer = customerAuthService.findCustomerByToken(token);
        customer.setAuthenticated(true);
    }
}
