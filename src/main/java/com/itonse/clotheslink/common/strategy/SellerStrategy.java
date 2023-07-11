package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.user.domain.Seller;
import com.itonse.clotheslink.user.service.SellerAuthService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SellerStrategy extends UserTypeStrategy {

    private final SellerAuthService sellerAuthService;

    public SellerStrategy(SellerAuthService sellerAuthService) {
        super(UserType.SELLER);
        this.sellerAuthService = sellerAuthService;
    }

    @Override
    protected boolean isAuthenticated(String token) {
        Seller seller = sellerAuthService.findSellerByToken(token);
        return seller.isAuthenticated();
    }

    @Override
    @Transactional
    protected void modifyAuthStatus(String token) {
        Seller seller = sellerAuthService.findSellerByToken(token);
        seller.setAuthenticated(true);
    }
}
