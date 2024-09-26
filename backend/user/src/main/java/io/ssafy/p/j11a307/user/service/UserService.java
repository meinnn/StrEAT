package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.entity.LeftUser;
import io.ssafy.p.j11a307.user.entity.User;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.repository.LeftUserRepository;
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
}
