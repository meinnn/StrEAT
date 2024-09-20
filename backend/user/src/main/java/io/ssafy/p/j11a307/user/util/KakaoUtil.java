package io.ssafy.p.j11a307.user.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoUtil {

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.app-key}")
    private String appKey;

    public String createKakaoRedirecUri() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + appKey + "&redirect_uri=" + redirectUri;
    }
}
