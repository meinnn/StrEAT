package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.service.LoginService;
import io.ssafy.p.j11a307.user.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    private final KakaoUtil kakaoUtil;

    @GetMapping("/kakao")
    public ResponseEntity<Void> kakaoLogin() {
        String uri = kakaoUtil.createKakaoRedirecUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(uri));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
