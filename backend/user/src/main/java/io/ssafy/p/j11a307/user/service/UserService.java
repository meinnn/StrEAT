package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.dto.OwnerProfile;
import io.ssafy.p.j11a307.user.dto.UserFcmTokenResponse;
import io.ssafy.p.j11a307.user.dto.UserInfoResponse;
import io.ssafy.p.j11a307.user.entity.LeftUser;
import io.ssafy.p.j11a307.user.entity.Owner;
import io.ssafy.p.j11a307.user.entity.User;
import io.ssafy.p.j11a307.user.entity.UserType;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.repository.CustomerRepository;
import io.ssafy.p.j11a307.user.repository.LeftUserRepository;
import io.ssafy.p.j11a307.user.repository.OwnerRepository;
import io.ssafy.p.j11a307.user.repository.UserRepository;
import io.ssafy.p.j11a307.user.service.userregistration.UserRegistrationFactory;
import io.ssafy.p.j11a307.user.service.userregistration.UserRegistrationStrategy;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LeftUserRepository leftUserRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;

    private final UserRegistrationFactory userRegistrationFactory;
    private final JwtUtil jwtUtil;

    public Integer getUserId(String accessToken) {
        return jwtUtil.getUserIdFromAccessToken(accessToken);
    }

    public boolean isOwner(Integer userId) {
        return ownerRepository.existsById(userId);
    }

    public boolean isCustomer(Integer userId) {
        return customerRepository.existsById(userId);
    }

    @Transactional
    public void withdraw(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        LeftUser leftUser = new LeftUser(user);
        userRepository.delete(user);
        leftUserRepository.save(leftUser);
    }

    @Transactional
    public void toggleOrderStatusAlert(Integer userId, boolean alertOn) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.toggleOrderStatusAlert(alertOn);
    }

    @Transactional
    public void toggleDibsStoreAlert(Integer userId, boolean alertOn) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.toggleDibsStoreAlert(alertOn);
    }

    public UserType getUserType(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if (customerRepository.existsById(userId)) {
            return UserType.CUSTOMER;
        }
        if (ownerRepository.existsById(userId)) {
            return UserType.OWNER;
        }
        return UserType.NOT_SELECTED;
    }

    @Transactional
    public Integer registerNewUserType(Integer userId, UserType registerUserType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        UserType userType = getUserType(userId);
        if (userType == UserType.CUSTOMER) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_CUSTOMER);
        }
        if (userType == UserType.OWNER) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_OWNER);
        }
        UserRegistrationStrategy userRegistrationStrategy =
                userRegistrationFactory.getUserRegistrationStrategy(registerUserType);
        User typeSelectedUser = userRegistrationStrategy.registerUser(user);
        userRepository.deleteById(userId);
        return typeSelectedUser.getId();
    }

    public UserInfoResponse getUserInfoById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.builder()
                .name(user.getUsername())
                .profileImgSrc(user.getProfileImgSrc())
                .orderStatusAlert(user.isOrderStatusAlert())
                .dibsStoreAlert(user.isDibsStoreAlert())
                .build();
    }

    public UserFcmTokenResponse getFcmTokenByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserFcmTokenResponse.builder()
                .fcmToken(user.getFcmToken())
                .build();
    }

    public OwnerProfile getAnnouncementOwnerInformation(Integer ownerId) {
        Owner owner = ownerRepository.findById(ownerId).orElseThrow(() -> new BusinessException(ErrorCode.OWNER_NOT_FOUND));
        return new OwnerProfile(owner);
    }
}
