package com.itonse.clotheslink.common.strategy;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.itonse.clotheslink.exception.ErrorCode.NOT_FOUND_USER_TYPE;

@RequiredArgsConstructor
@Component
public class MailAuthContext {
    /**
     * MailAuthContext 메소드를 생성하는 시점에 스프링은 스프링 컨테이너에서
     * UserTypeStrategy 를 구현하는 모든 빈을 가져와서 추가
     */
    private final List<UserTypeStrategy> userTypeStrategies;

    /**
     * userTypeStrategies 리스트에서 userType이 일치하는
     * Strategy 인스턴스를 찾아 해당 메소드 실행
     */
    public boolean isAuthenticated(UserType userType, String token) {
        return userTypeStrategies.stream()
                .filter(strategy -> strategy.getUserType() == userType)
                .findFirst()
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_TYPE))
                .isAuthenticated(token);
    }

    public void modifyAuthStatus(UserType userType, String token) {
        userTypeStrategies.stream()
                .filter(strategy -> strategy.getUserType() == userType)
                .findFirst()
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER_TYPE))
                .modifyAuthStatus(token);
    }
}
