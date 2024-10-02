package io.ssafy.p.j11a307.user.controller;

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

@RestController
@RequestMapping("/subscriptions")
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
}
