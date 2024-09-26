package io.ssafy.p.j11a307.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.user.entity.User;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.repository.UserRepository;
import io.ssafy.p.j11a307.user.util.KakaoUtil;
import io.ssafy.p.j11a307.user.vo.KakaoInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final String REDIS_LOGOUT_TIME_KEY_PREFIX = "logout-time:";

    private final UserRepository userRepository;

    private final KakaoUtil kakaoUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public Integer kakaoLogin(String kakaoTokens) throws JsonProcessingException {
        KakaoInfoVo kakaoUserInfo = kakaoUtil.getKakaoUserInfo(kakaoTokens);

        if (!isJoined(kakaoUserInfo.getKakaoId())) {
            join(kakaoUserInfo);
        }

        // 가입 절차를 밟았기 때문에 nullPointerException이 발생하지 않음이 보장됨
        User user = userRepository.findByKakaoId(kakaoUserInfo.getKakaoId()).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        // 재 가입하는 사람을 위해 refreshToken update
        user.refreshKakaoTokens(kakaoUserInfo.getAccessToken(), kakaoUserInfo.getRefreshToken());
        return user.getId();
    }

    @Transactional
    public void logout(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        registerLogoutTime(userId);
        user.logout();
    }

    public void autoLogin(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        try {
            kakaoUtil.refreshAccessToken(user.getKakaoRefreshToken(), user);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        }
    }

    private boolean isJoined(Long kakaoId) {
        return userRepository.existsByKakaoId(kakaoId);
    }

    private void join(KakaoInfoVo kakaoInfo) {
        User user = new User(kakaoInfo);
        userRepository.save(user);
    }

    private void registerLogoutTime(Integer userId) {
        Long logoutTime = System.currentTimeMillis();
        redisTemplate.opsForValue().set(REDIS_LOGOUT_TIME_KEY_PREFIX + userId, logoutTime);
    }
}
