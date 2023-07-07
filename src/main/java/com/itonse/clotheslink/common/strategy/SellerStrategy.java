package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.seller.domain.Seller;
import org.springframework.stereotype.Component;

@Component
public class SellerStrategy extends UserTypeStrategy {

    private final TokenService tokenService;

    public SellerStrategy(TokenService tokenService) {
        super(UserType.SELLER);
        this.tokenService = tokenService;
    }

    @Override
    protected boolean isAuthenticated(String token) {
        Seller seller = tokenService.findSellerByToken(token);
        return seller.isAuthenticated();
    }
}
