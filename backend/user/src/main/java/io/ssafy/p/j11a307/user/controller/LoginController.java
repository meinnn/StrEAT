package io.ssafy.p.j11a307.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.user.service.LoginService;
import io.ssafy.p.j11a307.user.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final KakaoUtil kakaoUtil;

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
        loginService.kakaoLogin(kakaoTokens);
        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok()
                .headers(headers).build();
    }
}
