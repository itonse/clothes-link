package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.common.UserType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public abstract class UserTypeStrategy {

    protected final UserType userType;

    protected abstract boolean isAuthenticated(String token);
}
