package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.dto.UserFcmTokenResponse;
import io.ssafy.p.j11a307.user.dto.UserInfoResponse;
import io.ssafy.p.j11a307.user.entity.UserType;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.global.DataResponse;
import io.ssafy.p.j11a307.user.service.UserService;
import io.ssafy.p.j11a307.user.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    @Tag(name = "내부 서비스 간 요청")
    public Integer getUserId(@RequestParam("accessToken") String accessToken, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (internalRequestKey.equals(internalRequest)) {
            return userService.getUserId(accessToken);
        }
        return null;
    }

    @GetMapping("/owner-id")
    @Operation(summary = "owner id 요청", description = "서비스내부에서 엑세스토큰으로 user id 요청, 클라이언트 요청 불가")
    @Parameters({
            @Parameter(name = "accessToken", description = "파싱이 필요한 user id")
    })
    @Tag(name = "내부 서비스 간 요청")
    public Integer getOwnerId(@RequestParam("accessToken") String accessToken, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (internalRequestKey.equals(internalRequest)) {
            Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
            boolean isOwner = userService.isOwner(userId);
            if (isOwner) {
                return userId;
            }
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }
        throw new BusinessException(ErrorCode.BAD_INNER_SERVICE_REQUEST);
    }

    @GetMapping("/customer-id")
    @Operation(summary = "customer id 요청", description = "서비스내부에서 엑세스토큰으로 user id 요청, 클라이언트 요청 불가")
    @Parameters({
            @Parameter(name = "accessToken", description = "파싱이 필요한 user id")
    })
    @Tag(name = "내부 서비스 간 요청")
    public Integer getCustomerId(@RequestParam("accessToken") String accessToken, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (internalRequestKey.equals(internalRequest)) {
            Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
            boolean isCustomer = userService.isCustomer(userId);
            if (isCustomer) {
                return userId;
            }
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        throw new BusinessException(ErrorCode.BAD_INNER_SERVICE_REQUEST);
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
            @ApiResponse(responseCode = "200", description = "손님으로 가입 성공, header에 새로운 토큰 발급"),
            @ApiResponse(responseCode = "404", description = "유저 id 기반 유저 없음"),
            @ApiResponse(responseCode = "400", description = "이미 손님으로 등록된 유저"),
            @ApiResponse(responseCode = "400", description = "이미 사장님으로 등록된 유저")
    })
    public ResponseEntity<Void> registerCustomer(@RequestHeader(HEADER_AUTH) String accessToken) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userId = userService.registerNewUserType(userId, UserType.CUSTOMER);
        HttpHeaders headers = jwtUtil.createTokenHeaders(userId);
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/owners/register")
    @Operation(summary = "회원가입 시 사장 선택", description = "회원 시 사장 선택")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장으로 가입 성공, header에 새로운 토큰 발급"),
            @ApiResponse(responseCode = "404", description = "유저 id 기반 유저 없음"),
            @ApiResponse(responseCode = "400", description = "이미 손님으로 등록된 유저"),
            @ApiResponse(responseCode = "400", description = "이미 사장님으로 등록된 유저")
    })
    public ResponseEntity<Void> registerOwner(@RequestHeader(HEADER_AUTH) String accessToken) {
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        userId = userService.registerNewUserType(userId, UserType.OWNER);
        HttpHeaders headers = jwtUtil.createTokenHeaders(userId);
        return ResponseEntity.ok().headers(headers).build();
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 유저 없음")
    })
    public ResponseEntity<UserInfoResponse> getUserInformation(@PathVariable Integer userId) {
        UserInfoResponse userInfoResponse = userService.getUserInfoById(userId);
        return ResponseEntity.ok(userInfoResponse);
    }

    @GetMapping("/profile")
    @Operation(summary = "유저 정보 조회", description = "유저 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 유저 없음")
    })
    public ResponseEntity<UserInfoResponse> getUserInformation(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        Integer userId = jwtUtil.getUserIdFromAccessToken(accessToken);
        UserInfoResponse userInfoResponse = userService.getUserInfoById(userId);
        return ResponseEntity.ok(userInfoResponse);
    }

    @GetMapping("/createToken")
    @Operation(summary = "테스트용 토큰 생성", description = "테스트용 토큰 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 생성 완료")
    })
    @Parameters({
            @Parameter(name = "userId", in = ParameterIn.QUERY)
    })
    @Tag(name = "토큰 생성")
    public ResponseEntity<String> createToken(@RequestParam Integer userId) {
        String token = jwtUtil.createAccessToken(userId);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{userId}/fcm-token")
    @Operation(summary = "유저 아이디로 fcm token 받기", description = "유저 아이디로 fcm token 받기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fcm token 발급 완료"),
            @ApiResponse(responseCode = "400", description = "내부에서만 요청 가능한 서비스입니다."),
            @ApiResponse(responseCode = "404", description = "id에 해당하는 user가 업습니다.")
    })
    @Tag(name = "내부 서비스 간 요청")
    public ResponseEntity<DataResponse<UserFcmTokenResponse>> getUserFcmToken(
            @PathVariable Integer userId, @RequestHeader("X-Internal-Request") String internalRequest) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_INNER_SERVICE_REQUEST);
        }
        UserFcmTokenResponse userFcmTokenResponse = userService.getFcmTokenByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("fcm token 조회에 성공했습니다.", userFcmTokenResponse));
    }
}
