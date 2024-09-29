package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.service.UserService;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Value("${streat.internal-request}")
    private String internalRequestKey;
    private final String HEADER_AUTH = "Authorization";

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @GetMapping("/user-id")
    @Operation(summary = "user id 요청", description = "서비스에서 엑세스토큰으로 user id 요청, 클라이언트 요청 불가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, user id 반환")
    })
    @Parameters({
            @Parameter(name = "accessToken", description = "파싱이 필요한 user id")
    })
    public Integer getUserId(String accessToken, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (internalRequestKey.equals(internalRequest)) {
            return userService.getUserId(accessToken);
        }
        return null;
    }

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "탈퇴 성공")
    })
    public ResponseEntity<Void> withdraw(@RequestHeader(HEADER_AUTH) String accessToken) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userService.withdraw(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/order-status-alert")
    @Operation(summary = "주문 상황 알림 on/off", description = "주문 상황 알림 on/off")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 알림 on/off")
    })
    @Parameters({
            @Parameter(name = "alertOn", description = "알림 설정 여부", example = "true: 켜기, false: 끄기")
    })
    public ResponseEntity<Void> toggleOrderStatusAlert(@RequestHeader(HEADER_AUTH) String accessToken, boolean alertOn) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userService.toggleOrderStatusAlert(userId, alertOn);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dibs-store-alert")
    @Operation(summary = "찜 가게 영업 시작 알림 on/off", description = "찜 가게 영업 시작 알림 on/off")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 알림 on/off")
    })
    @Parameters({
            @Parameter(name = "alertOn", description = "알림 설정 여부", example = "true: 켜기, false: 끄기")
    })
    public ResponseEntity<Void> toggleDibsStoreAlert(@RequestHeader(HEADER_AUTH) String accessToken, boolean alertOn) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userService.toggleDibsStoreAlert(userId, alertOn);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/customers/register")
    @Operation(summary = "회원가입 시 손님 선택", description = "회원가입 시 손님 선택")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "손님으로 가입 성공"),
            @ApiResponse(responseCode = "404", description = "유저 id 기반 유저 없음"),
            @ApiResponse(responseCode = "400", description = "이미 손님으로 등록된 유저"),
            @ApiResponse(responseCode = "400", description = "이미 사장님으로 등록된 유저")
    })
    public ResponseEntity<Void> registerCustomer(@RequestHeader(HEADER_AUTH) String accessToken) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userService.registerNewCustomer(userId);
        return ResponseEntity.ok().build();
    }
}
