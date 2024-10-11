package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.dto.*;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.ssafy.p.j11a307.order.global.MessageResponse;
import io.ssafy.p.j11a307.order.service.OrderService;
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
@RequiredArgsConstructor
@RequestMapping("/order-request")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

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

    @GetMapping("/info/{ordersId}")
    @Operation(summary = "주문 내역 상세조회", description = "해당 주문 id의 상세조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문내역 상세조회 성공"),
            @ApiResponse(responseCode = "404", description = "주문내역 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "상세조회 권한 없음"),
    })
    public ResponseEntity<DataResponse<GetOrderDetailDTO>> getOrderDetail(@PathVariable Integer ordersId,
                                                                          @RequestHeader("Authorization") String token) {

        GetOrderDetailDTO getOrderDetailDTO = orderService.getOrderDetail(ordersId, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("주문 내역 상세조회에 성공했습니다.", getOrderDetailDTO));
    }


    @PostMapping("/{storeId}/search")
    @Operation(summary = "주문 내역 검색", description = "검색어와 태그, 날짜에 따른 검색 결과 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "점포 주문내역 검색 성공"),
            @ApiResponse(responseCode = "404", description = "점포 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "검색 권한 없음"),
            @ApiResponse(responseCode = "400", description = "시작-끝 시간 중 하나만 입력함")
    })
    public ResponseEntity<DataResponse<OrderSearchResponse>> getSearchOrders(@PathVariable Integer storeId,
                                                                          @RequestBody OrderSearchRequest orderSearchRequest,
                                                                          @RequestHeader("Authorization") String token) {
        OrderSearchResponse orderSearchResponse = orderService.getSearchOrders(storeId, orderSearchRequest, token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("주문 내역 검색에 성공했습니다.", orderSearchResponse));
    }

    @GetMapping("/mine/list/ongoing")
    @Operation(summary = "진행중인 내 주문 내역 조회", description = "내 주문 내역 중 진행중인 주문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "진행중인 내 주문 내역 조회 성공"),
    })
     public ResponseEntity<DataResponse<GetMyOngoingOrderDTO>> getMyOngoingOrder(@RequestHeader("Authorization") String token) {

        GetMyOngoingOrderDTO getMyOngoingOrderDTO = orderService.getMyOngoingOrder(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("진행중인 내 주문 내역 조회에 성공했습니다.", getMyOngoingOrderDTO));
    }


    @GetMapping("/{storeId}/list/waiting")
    @Operation(summary = "전체 대기 팀/메뉴 조회", description = "해당 점포에서 현재 대기중인 팀과 메뉴 개수를 반환한다.")
    public ResponseEntity<DataResponse<GetStoreWaitingDTO>> getStoreWaiting(@PathVariable Integer storeId) {

        GetStoreWaitingDTO getStoreWaitingDTO = orderService.getStoreWaiting(storeId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("대기 팀/메뉴 조회에 성공했습니다.", getStoreWaitingDTO));

    }

    @PostMapping("/{storeId}")
    @Operation(summary = "주문번호 발급", description = "결제를 시작할 때 주문번호를 우선 발급받는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "주문번호 발급 성공"),
    })
    public ResponseEntity<DataResponse<String>> createOrderNumber(@PathVariable Integer storeId,
                                                                  @RequestBody CreateOrderNumberRequest createOrderNumberRequest,
                                                                  @RequestHeader("Authorization") String token) {
        String orderNum = orderService.createOrderNumber(storeId, createOrderNumberRequest, token);

        return ResponseEntity.status(HttpStatus.CREATED).body(DataResponse.of("주문번호 발급에 성공했습니다.", orderNum));
    }


    @PostMapping("/pay-complete")
    @Operation(summary = "결제 성공/실패 처리", description = "결제 성공/실패에 따라 처리한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 성공/실패 처리 완료."),
            @ApiResponse(responseCode = "400", description = "지원하지 않는 결제 방식이거나 이미 처리 완료된 결제임"),
            @ApiResponse(responseCode = "404", description = "주문내역이 존재하지 않음")
    })
    @Tag(name = "내부 서비스 간 요청")
    public ResponseEntity<DataResponse<Integer>> paymentProcessing(@RequestBody PayProcessRequest payProcessRequest,
                                                             @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (internalRequestKey.equals(internalRequest)) {
             Integer orderId = orderService.paymentProcessing(payProcessRequest);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(DataResponse.of("결제 성공/실패 처리를 완료했습니다.", orderId));
        }
        return null;
    }

    //주문 취소

    //NFC 태그 후 손님의 가장 최근 주문번호 조회

    //NFC 태그 후 수령 완료하기
    @PostMapping("/pick-up")
    @Operation(summary = "음식 수령하기", description = "음식 수령하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식 수령 성공")
    })
    public ResponseEntity<DataResponse<PickupCompletedResponse>> pickupFood(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        PickupCompletedResponse pickupCompletedResponse= orderService.pickupFood(token, 14);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("음식 수령에 성공했습니다.", pickupCompletedResponse));
    }
}
