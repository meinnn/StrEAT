package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.dto.SubscriptionStoreResponse;
import io.ssafy.p.j11a307.user.global.DataResponse;
import io.ssafy.p.j11a307.user.global.MessageResponse;
import io.ssafy.p.j11a307.user.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class SubscriptionController {

    private final String HEADER_AUTH = "Authorization";

    private final SubscriptionService subscriptionService;

    @PostMapping("/{storeId}")
    @Operation(summary = "가게 구독", description = "가게 구독")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 구독 성공")
    })
    public ResponseEntity<Void> subscriptStore(@PathVariable Integer storeId, HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        subscriptionService.subscript(accessToken, storeId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{storeId}")
    @Operation(summary = "가게 구독 취소", description = "가게 구독 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "가게 구독 취소 성공")
    })
    public ResponseEntity<Void> unsubscriptStore(@PathVariable Integer storeId, HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        subscriptionService.unsubscribe(accessToken, storeId);
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
        subscriptionService.changeStoreAlertStatus(accessToken, storeId, true);
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
        subscriptionService.changeStoreAlertStatus(accessToken, storeId, false);
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.of("알림 끄기 성공"));
    }
}
