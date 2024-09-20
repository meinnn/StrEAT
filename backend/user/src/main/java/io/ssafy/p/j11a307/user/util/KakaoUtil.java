package io.ssafy.p.j11a307.user.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssafy.p.j11a307.user.vo.KakaoInfoVo;
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

    @Value("${kakao.user-info.url}")
    private String KAKAO_USER_INFO_URL;

    private final String ACCESS_TOKEN = "access_token";
    private final String REFRESH_TOKEN = "refresh_token";

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

    public KakaoInfoVo getKakaoUserInfo(String kakaoResponse) throws JsonProcessingException {
        String kakaoAccessToken = extractToken(kakaoResponse, ACCESS_TOKEN);
        String kakaoRefreshToken = extractToken(kakaoResponse, REFRESH_TOKEN);

        // Http Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoAccessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        ResponseEntity<String> response = getResponseFromExternalApi(
                kakaoUserInfoRequest, HttpMethod.POST, KAKAO_USER_INFO_URL);

        // responseBody에 있는 정보 꺼내기
        String responseBody = response.getBody();
        return parseKakaoUserInfo(responseBody, kakaoAccessToken, kakaoRefreshToken);
    }

    private ResponseEntity<String> getResponseFromExternalApi(HttpEntity<MultiValueMap<String, String>> requestHttpEntity,
                                                             HttpMethod httpMethod, String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, httpMethod, requestHttpEntity, String.class);
    }

    private String extractToken(String kakaoResponse, String tokenType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(kakaoResponse);
        if (jsonNode.has(tokenType)) {
            return jsonNode.get(tokenType).asText();
        }
        return null; // refreshToken의 경우, 만료일이 1달 이내인 경우에만 갱신되기 때문에 널일 수 있다.
    }

    private KakaoInfoVo parseKakaoUserInfo(String kakaoResponse, String accessToken, String refreshToken) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(kakaoResponse);
        JsonNode properties = jsonNode.get("properties");
        return KakaoInfoVo.builder()
                .kakaoId(jsonNode.get("id").asLong())
                .nickname(properties.get("nickname").asText())
                .imageSrc(properties.get("thumbnail_image").asText())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
