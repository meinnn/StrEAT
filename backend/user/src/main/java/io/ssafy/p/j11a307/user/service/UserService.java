package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.repository.UserRepository;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public Integer getUserId(String accessToken) {
        return jwtUtil.getUserIdFromAccessToken(accessToken);
    }
}
