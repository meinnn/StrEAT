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
    private final OwnerRegistrationStrategy ownerRegistrationStrategy;

    public UserRegistrationStrategy getUserRegistrationStrategy(UserType userType) {
        return switch (userType) {
            case CUSTOMER -> customerRegistrationStrategy;
            case OWNER -> ownerRegistrationStrategy;
            default -> throw new BusinessException(ErrorCode.BAD_USER_TYPE_REQUEST);
        };
    }
}
