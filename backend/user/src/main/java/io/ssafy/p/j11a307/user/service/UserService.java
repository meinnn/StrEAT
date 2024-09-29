package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.entity.Customer;
import io.ssafy.p.j11a307.user.entity.LeftUser;
import io.ssafy.p.j11a307.user.entity.User;
import io.ssafy.p.j11a307.user.entity.UserType;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.repository.CustomerRepository;
import io.ssafy.p.j11a307.user.repository.LeftUserRepository;
import io.ssafy.p.j11a307.user.repository.OwnerRepository;
import io.ssafy.p.j11a307.user.repository.UserRepository;
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

    private final JwtUtil jwtUtil;

    public Integer getUserId(String accessToken) {
        return jwtUtil.getUserIdFromAccessToken(accessToken);
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
    public void registerNewCustomer(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Customer customer = Customer.builder().userId(userId).build();
        customerRepository.save(customer);
    }
}
