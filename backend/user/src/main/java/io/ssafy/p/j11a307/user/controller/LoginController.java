package io.ssafy.p.j11a307.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.user.service.LoginService;
import io.ssafy.p.j11a307.user.service.UserService;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import io.ssafy.p.j11a307.user.util.KakaoUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final String HEADER_AUTH = "Authorization";

    private final LoginService loginService;
    private final UserService userService;

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
    @Operation(summary = "인가코드 부여", description = "카카오에서 받은 인가코드를 요청할 때 사용")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인가코드 요청 성공",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string")))
    })
    @Parameters({
            @Parameter(name = "code", description = "카카오에서 받은 인가코드(직접 생성 x)")
    })
    public ResponseEntity<String> kakaoAuth(String code) throws JsonProcessingException {
        String kakaoTokens = kakaoUtil.getKakaoTokens(code);
        Integer userId = loginService.kakaoLogin(kakaoTokens);
        HttpHeaders headers = jwtUtil.createTokenHeaders(userId);
        String userType = userService.getUserType(userId);
        return new ResponseEntity<>(userType, headers, HttpStatus.OK);
    }

    @PostMapping("/login-auto")
    @Operation(summary = "토큰 활용 자동 로그인", description = "streat 서비스 토큰을 활용한 자동 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자동 로그인 성공"),
            @ApiResponse(responseCode = "404", description = "User ID가 없어 로그인 실패"),
            @ApiResponse(responseCode = "401", description = "토큰 기간 만료, 재 로그인 필요")
    })
    @Parameters({
            @Parameter(name = "code", description = "카카오에서 받은 인가코드(직접 생성 x)")
    })
    public ResponseEntity<Void> autoLogin(@RequestHeader(HEADER_AUTH) String accessToken) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        loginService.autoLogin(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "로그아웃", description = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(HEADER_AUTH) String accessToken) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        loginService.logout(userId);
        return ResponseEntity.ok().build();
    }
}
