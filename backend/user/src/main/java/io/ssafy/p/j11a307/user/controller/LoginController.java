package io.ssafy.p.j11a307.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.user.service.LoginService;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import io.ssafy.p.j11a307.user.util.KakaoUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private String HEADER_AUTH = "Authorization";

    private final LoginService loginService;

    private final KakaoUtil kakaoUtil;
    private final JwtUtil jwtUtil;

    @GetMapping("/login/kakao")
    public ResponseEntity<Void> kakaoLogin() {
        String uri = kakaoUtil.createKakaoRedirecUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(uri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/login/kakao/auth")
    public ResponseEntity<Void> kakaoAuth(String code) throws JsonProcessingException {
        String kakaoTokens = kakaoUtil.getKakaoTokens(code);
        Integer userId = loginService.kakaoLogin(kakaoTokens);
        HttpHeaders headers = jwtUtil.createTokenHeaders(userId);

        return ResponseEntity.ok()
                .headers(headers).build();
    }

    @PostMapping("/login-auto")
    public ResponseEntity<Void> autoLogin(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        loginService.autoLogin(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        loginService.logout(userId);
        return ResponseEntity.ok().build();
    }
}
