package io.ssafy.p.j11a307.user.util;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
public class JwtUtilTest {

    private String salt = "testSaltValueTestSaltValuetestSaltValueTestSaltValuetestSaltValueTestSaltValue";

    private long accessTokenExpireTime = 10000L;

    JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil();
        log.info("salt: {}", salt);
        ReflectionTestUtils.setField(jwtUtil, "salt", salt);
        ReflectionTestUtils.setField(jwtUtil, "accessTokenExpireTime", accessTokenExpireTime);
    }

    @Test
    void createAndParseTokenTest() {
        Integer userId = 1;
        String accessToken = jwtUtil.createAccessToken(userId);
        log.info("accessToken: {}", accessToken);

        Assertions.assertThat(jwtUtil.getUserIdFromAccessToken(accessToken)).isEqualTo(userId);
    }
}
