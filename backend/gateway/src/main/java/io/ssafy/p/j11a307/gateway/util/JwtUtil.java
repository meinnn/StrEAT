package io.ssafy.p.j11a307.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class JwtUtil {

    @Value("${jwt.salt}")
    private String salt;

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
}
