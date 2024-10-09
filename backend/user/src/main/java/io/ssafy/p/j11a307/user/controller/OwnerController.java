package io.ssafy.p.j11a307.user.controller;

import io.ssafy.p.j11a307.user.dto.OwnerInfoResponse;
import io.ssafy.p.j11a307.user.global.DataResponse;
import io.ssafy.p.j11a307.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnerController {

    private final String HEADER_AUTH = "Authorization";

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "사장 정보 조회", description = "사장 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사장 정보 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사장이 아닙니다.")
    })
    @Tag(name = "owner")
    public ResponseEntity<DataResponse<OwnerInfoResponse>> getOwnerProfile(HttpServletRequest request) {
        String accessToken = request.getHeader(HEADER_AUTH);
        OwnerInfoResponse ownerInfoResponse = userService.getOwnerInfo(accessToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("사장 정보 조회에 성공했습니다.", ownerInfoResponse));
    }
}
