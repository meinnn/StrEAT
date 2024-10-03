package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.GetMyOrderDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreOrderDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreReviewDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.global.MessageResponse;
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
@RequestMapping("/order-request")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{storeId}/list")
    @Operation(summary = "점포별 주문내역 조회", description = "현황별 해당 id 점포의 주문내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "점포별 주문 내역 조회 성공"),
            @ApiResponse(responseCode = "404", description = "점포 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
    })
    @Parameters({
            @Parameter(name = "pgno", description = "페이지 번호(0번부터 시작)"),
            @Parameter(name = "spp", description = "한 페이지에 들어갈 개수"),
            @Parameter(name = "status", description = "주문 상태(PROCESSING/RECEIVING)")
    })
    public ResponseEntity<DataResponse<GetStoreOrderDTO>> getStoreOrderList(@PathVariable Integer storeId,
                                                                            @RequestParam Integer pgno,
                                                                            @RequestParam Integer spp,
                                                                            @RequestParam String status,
                                                                            @RequestHeader("Authorization") String token) {

        GetStoreOrderDTO getStoreOrderDTO = orderService.getStoreOrderList(storeId, pgno, spp, status, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포별 주문내역 조회에 성공했습니다.", getStoreOrderDTO));
    }

    @GetMapping("/{ordersId}/handle")
    @Operation(summary = "주문 승인/거절", description = "대기 중인 주문을 승인하거나 거절")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 승인/거절 성공"),
            @ApiResponse(responseCode = "404", description = "주문 내역 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "주문이 수락/거절 대기 상태가 아니거나, 올바르지 않은 flag"),
    })
    @Parameters({
            @Parameter(name = "flag", description = "거절 시: 0, 승인 시: 1"),
    })
    public ResponseEntity<MessageResponse> handleOrders(@PathVariable Integer ordersId,
                                                        @RequestParam Integer flag,
                                                        @RequestHeader("Authorization") String token) {

        orderService.handleOrders(ordersId, flag, token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("대기 중인 주문 처리에 성공했습니다."));
    }


    @GetMapping("/{ordersId}/handle/complete")
    @Operation(summary = "조리 완료", description = "조리 중인 주문을 완료하고 수령 대기중으로")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문 완료 성공"),
            @ApiResponse(responseCode = "404", description = "주문 내역 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "권한 없음"),
            @ApiResponse(responseCode = "400", description = "주문이 조리 상태가 아님"),
    })
    public ResponseEntity<MessageResponse> handleCompleteOrders(@PathVariable Integer ordersId,
                                                        @RequestHeader("Authorization") String token) {
        orderService.handleCompleteOrders(ordersId, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("조리 중인 주문 처리에 성공했습니다."));
    }


    @GetMapping("/mine/list")
    @Operation(summary = "내 주문내역 리스트 조회", description = "날짜별로 내 주문 내역을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "나의 주문내역 조회 성공"),
    })
    @Parameters({
            @Parameter(name = "pgno", description = "페이지 번호(0번부터 시작)"),
            @Parameter(name = "spp", description = "한 페이지에 들어갈 개수"),
    })
    public ResponseEntity<DataResponse<GetMyOrderDTO>> getMyOrderList(@RequestParam Integer pgno,
                                                                      @RequestParam Integer spp,
                                                                      @RequestHeader("Authorization") String token)  {

        GetMyOrderDTO getMyOrderDTO = orderService.getMyOrderList(pgno, spp, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("내 주문내역 리스트 조회에 성공했습니다.", getMyOrderDTO));
    }






    //주문 내역 검색



    //주문 내역 상세조회

    //대기 팀 조회

    //내 주문 상세 조회

    //주문 취소


    //NFC 태그 후 손님의 가장 최근 주문번호 조회

    //NFC 태그 후 수령 완료하기


}
