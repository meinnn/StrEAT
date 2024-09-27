package io.ssafy.p.j11a307.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${streat.redis.logout-prefix}")
    private String redisLogoutPrefix;

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean checkToken(String token) {
        try {
//			Json Web Signature? 서버에서 인증을 근거로 인증 정보를 서버의 private key 서명 한것을 토큰화 한것
//			setSigningKey : JWS 서명 검증을 위한  secret key 세팅
//			parseClaimsJws : 파싱하여 원본 jws 만들기
            Jws<Claims> claims = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(extractAccessToken(token));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExpired(String token) {
        Integer userId = getUserIdFromAccessToken(token);
        Long lastLogoutTime = (Long) redisTemplate.opsForValue().get(redisLogoutPrefix + userId);
        Claims claims = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(extractAccessToken(token)).getBody();
        long issuedAt = claims.getIssuedAt().getTime();
        return lastLogoutTime != null && lastLogoutTime > issuedAt;
    }

    private String extractAccessToken(String accessToken) {
        return accessToken.replace("Bearer ", "");
    }

    private byte[] generateKey() {
        try {
//            charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩 됨.
            return salt.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private Integer getUserIdFromAccessToken(String accessToken) {
        Claims claims = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(extractAccessToken(accessToken)).getBody();
        return (Integer) claims.get("userId");
    }
}
