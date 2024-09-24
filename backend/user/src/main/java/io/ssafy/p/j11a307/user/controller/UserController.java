package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user-id")
    public Integer getUserId(String accessToken) {
        return userService.getUserId(accessToken);
    }
}
