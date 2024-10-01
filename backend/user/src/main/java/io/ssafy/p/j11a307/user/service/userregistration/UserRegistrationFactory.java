package io.ssafy.p.j11a307.user.service.userregistration;

import io.ssafy.p.j11a307.user.entity.UserType;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationFactory {

    private final CustomerRegistrationStrategy customerRegistrationStrategy;

    public UserRegistrationStrategy getUserRegistrationStrategy(UserType userType) {
        switch (userType) {
            case CUSTOMER:
                return customerRegistrationStrategy;
            default:
                throw new BusinessException(ErrorCode.BAD_USER_TYPE_REQUEST);
        }
    }
}
