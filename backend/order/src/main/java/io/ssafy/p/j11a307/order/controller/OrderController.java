package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.GetStoreOrderDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreReviewDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    //점포별 주문내역 조회(현황별)
    @GetMapping("/stores/{storeId}/list")
    @Operation(summary = "점포별 주문내역 조회", description = "현황별 해당 id 점포의 주문내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "점포별 주문 내역 조회 성공"),
            @ApiResponse(responseCode = "404", description = "점포 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),

    })
    @Parameters({
            @Parameter(name = "pgno", description = "페이지 번호(0번부터 시작)"),
            @Parameter(name = "spp", description = "한 페이지에 들어갈 개수"),
            @Parameter(name = "status", description = "주문 상태(WAITING_FOR_PROCESSING/PROCESSING/WAITING_FOR_RECEIPT/RECEIVED")
    })
    public ResponseEntity<DataResponse<GetStoreOrderDTO>> getStoreOrderList(@PathVariable Integer storeId,
                                                                            @RequestParam Integer pgno,
                                                                            @RequestParam Integer spp,
                                                                            @RequestHeader("Authorization") String token) {

        GetStoreOrderDTO getStoreOrderDTO = orderService.getStoreOrderList(storeId, pgno, spp, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포별 주문내역 조회에 성공했습니다.", getStoreOrderDTO));

    }




    //주문 승인/거절

    //조리 완료하기

    //주문 내역 검색

    //주문 내역 상세조회

    //내 주문내역 리스트 조회(날짜별)

    //대기 팀 조회

    //내 주문 상세 조회

    //주문 취소


    //NFC 태그 후 손님의 가장 최근 주문번호 조회

    //NFC 태그 후 수령 완료하기


}
