package io.ssafy.p.j11a307.user.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoUtil {

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.app-key}")
    private String appKey;

    @Value("${kakao.token.url}")
    private String KAKAO_TOKEN_URL;

    public String createKakaoRedirecUri() {
        return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + appKey + "&redirect_uri=" + redirectUri;
    }

    public String getKakaoTokens(String code) {
        // HTTP header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", appKey);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = getResponseFromExternalApi(
                kakaoTokenRequest, HttpMethod.POST, KAKAO_TOKEN_URL);

        return response.getBody();
    }

    private ResponseEntity<String> getResponseFromExternalApi(HttpEntity<MultiValueMap<String, String>> requestHttpEntity,
                                                             HttpMethod httpMethod, String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, httpMethod, requestHttpEntity, String.class);
    }
}
