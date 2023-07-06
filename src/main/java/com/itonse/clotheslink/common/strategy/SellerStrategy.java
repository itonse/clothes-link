package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.admin.service.TokenService;
import com.itonse.clotheslink.seller.domain.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SellerStrategy implements UserTypeStrategy {

    private final TokenService tokenService;

    @Override
    public boolean isAuthenticated(String token) {
        Seller seller = tokenService.findSellerByToken(token);
        return seller.isAuthenticated();
    }
}
