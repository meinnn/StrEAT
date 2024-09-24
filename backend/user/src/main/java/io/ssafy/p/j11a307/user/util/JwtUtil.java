package io.ssafy.p.j11a307.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    public String createAccessToken(Integer userId) {
        return create(userId, "access-token", accessTokenExpireTime);
    }

    public Integer getUserIdFromAccessToken(String accessToken) {
        Claims claims = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(extractAccessToken(accessToken)).getBody();
        return (Integer) claims.get("userId");
    }

    public HttpHeaders createTokenHeaders(Integer userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", createAccessToken(userId));
        return headers;
    }

    private String create(Integer userId, String subject, long expireTime) {
//		Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration),
//		토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
        Claims claims = Jwts.claims()
                .setSubject(subject) // 토큰 제목 설정 ex) access-token, refresh-token
                .setIssuedAt(new Date()) // 생성일 설정
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)); // 만료일 설정(유효기간)

//        저장할 data의 key, value

        claims.put("userId", userId);

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT").setClaims(claims) // Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
                .signWith(SignatureAlgorithm.HS256, generateKey()) // Signature 설정 : secret key를 활용한 암호화.
                .compact();// 직렬화 처리

        return jwt;
    }

    private byte[] generateKey() {
        try {
//            charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩 됨.
            return salt.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private String extractAccessToken(String accessToken) {
        return accessToken.replace("Bearer ", "");
    }
}
