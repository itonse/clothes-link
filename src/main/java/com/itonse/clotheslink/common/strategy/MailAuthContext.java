package com.itonse.clotheslink.common.strategy;

import org.springframework.stereotype.Component;

@Component
public class MailAuthContext {

    private UserTypeStrategy userTypeStrategy;

    public void setUserTypeStrategy(UserTypeStrategy userTypeStrategy) {
        this.userTypeStrategy = userTypeStrategy;
    }

    public boolean isAuthenticated(String token) {
        return userTypeStrategy.isAuthenticated(token);
    }
}
