package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.entity.LeftUser;
import io.ssafy.p.j11a307.user.entity.User;
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
        User user = userRepository.findById(userId).orElseThrow();
        LeftUser leftUser = new LeftUser(user);
        userRepository.delete(user);
        leftUserRepository.save(leftUser);
    }

    @Transactional
    public void toggleOrderStatusAlert(Integer userId, boolean alertOn) {
        User user = userRepository.findById(userId).orElseThrow();
        user.toggleOrderStatusAlert(alertOn);
    }
}
