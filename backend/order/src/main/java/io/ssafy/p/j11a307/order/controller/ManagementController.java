package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.GetDailySalesDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.service.ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-manage")
@CrossOrigin
public class ManagementController {
    private final ManagementService managementService;

    //달, 일별 매출 리스트 조회
    @GetMapping("/day-list")
    @Operation(summary = "일별 매출 조회", description = "선택한 달의 일별 매출을 리스트로 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "주문 승인/거절 성공"),
//            @ApiResponse(responseCode = "404", description = "주문 내역 존재하지 않음"),
//            @ApiResponse(responseCode = "401", description = "권한 없음"),
//            @ApiResponse(responseCode = "400", description = "주문이 수락/거절 대기 상태가 아니거나, 올바르지 않은 flag"),
//    })
//    @Parameters({
//            @Parameter(name = "flag", description = "거절 시: 0, 승인 시: 1"),
//    })
    public ResponseEntity<DataResponse<Map<Integer, GetDailySalesDTO>>> getDailySalesList(@RequestParam("month") Integer month,
                                                                                          @RequestHeader("Authorization") String token) {
        Map<Integer, GetDailySalesDTO> dailySalesDTOMap = managementService.getDailySalesList(month, token);

        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("일별 매출 조회에 성공했습니다.", dailySalesDTOMap));
    }
}