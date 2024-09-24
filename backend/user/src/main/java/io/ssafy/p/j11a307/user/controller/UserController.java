package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.service.UserService;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Value("{streat.internal-request}")
    private String internalRequestKey;
    private String HEADER_AUTH = "Authorization";

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @GetMapping("/user-id")
    public Integer getUserId(String accessToken, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (internalRequestKey.equals(internalRequest)) {
            return userService.getUserId(accessToken);
        }
        return null;
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userService.withdraw(userId);
        return ResponseEntity.noContent().build();
    }
}
