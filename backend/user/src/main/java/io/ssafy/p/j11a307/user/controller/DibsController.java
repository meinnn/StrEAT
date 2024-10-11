package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.dto.StoreDibsResponse;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.global.DataResponse;
import io.ssafy.p.j11a307.user.global.MessageResponse;
import io.ssafy.p.j11a307.user.service.DibsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dibs")
@RequiredArgsConstructor
public class DibsController {

    @Value("${streat.internal-request}")
    private String internalRequestKey;
    private final String HEADER_AUTH = "Authorization";

    private final DibsService dibsService;

    @PostMapping("/{storeId}")
    @Operation(summary = "가게 구독", description = "가게 구독")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 구독 성공")
    })
    public ResponseEntity<Void> subscriptStore(@PathVariable Integer storeId, HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        dibsService.subscript(accessToken, storeId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "가게 구독 취소", description = "가게 구독 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 구독 취소 성공")
    })
    public ResponseEntity<Void> unsubscriptStore(@PathVariable Integer storeId, HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        dibsService.unsubscribe(accessToken, storeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/alert/{storeId}")
    @Operation(summary = "가게 알림 켜기", description = "가게 알림 켜기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 알림 켜기 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 유저입니다."),
            @ApiResponse(responseCode = "400", description = "찜한 가게가 아닙니다.")
    })
    public ResponseEntity<MessageResponse> storeAlertOn(@PathVariable Integer storeId, HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        dibsService.changeStoreAlertStatus(accessToken, storeId, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.of("알림 켜기 성공"));
    }

    @DeleteMapping("/alert/{storeId}")
    @Operation(summary = "가게 알림 끄기", description = "가게 알림 끄기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 알림 끄기 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 유저입니다."),
            @ApiResponse(responseCode = "400", description = "찜한 가게가 아닙니다.")
    })
    public ResponseEntity<MessageResponse> storeAlertOff(@PathVariable Integer storeId, HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        dibsService.changeStoreAlertStatus(accessToken, storeId, false);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.of("알림 끄기 성공"));
    }

    @GetMapping
    @Operation(summary = "찜 목록 조회", description = "찜 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찜 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 유저입니다.")
    })
    public ResponseEntity<DataResponse<List<StoreDibsResponse>>> getAllDibs(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        List<StoreDibsResponse> allDibs = dibsService.getAllDibs(accessToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("찜 목록 조회에 성공했습니다.", allDibs));
    }

    @GetMapping("/{storeId}/called")
    @Operation(summary = "스토어 찜 여부 조회", description = "스토어 찜 여부 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "찜 여부 조회 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 유저입니다.")
    })
    public ResponseEntity<DataResponse<Boolean>> calledDibs(HttpServletRequest request, @PathVariable Integer storeId) {
        String accessToken = request.getHeader(HEADER_AUTH);
        Boolean calledDibs = dibsService.calledDibs(accessToken, storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("스토어 찜 여부 조회에 성공했습니다.", calledDibs));
    }

    @GetMapping("/{storeId}/dibs-customers")
    @Operation(summary = "store를 찜한 유저 아이디 리스트 조회", description = "store를 찜한 유저 아이디 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 아이디 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "외부에서는 접근할 수 없습니다.")
    })
    @Tag(name = "내부 서비스 간 요청")
    public ResponseEntity<DataResponse<List<Integer>>> getCalledDibsUserByStoreId(
            @PathVariable("storeId") Integer storeId, @RequestHeader("X-Internal-Request") String internalRequest) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_INNER_SERVICE_REQUEST);
        }
        List<Integer> calledDibsUserIds = dibsService.getCalledDibsUserIds(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("유저 리스트 조회 성공", calledDibsUserIds));
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
        dibsService.toggleOrderStatusAlert(accessToken, alertOn);
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
        dibsService.toggleDibsStoreAlert(accessToken, alertOn);
        return ResponseEntity.ok().build();
    }
}
