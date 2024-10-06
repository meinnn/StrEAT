package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.PayTypeCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetOrderDetailDTO (
    @Schema(description = "주문내역 id", example = "1")
    Integer ordersId,

    @Schema(description = "주문번호", example = "A237482374")
    String orderNumber,

    @Schema(description = "주문 들어온 시각", example = "2024-09-30 12:34:56.000000")
    LocalDateTime orderCreatedAt,

    @Schema(description = "수령 시각", example = "2024-09-30 14:34:56.000000")
    LocalDateTime orderReceivedAt,

    @Schema(description = "결제 타입", example = "CARD")
    PayTypeCode paymentMethod,

    @Schema(description = "주문 상태", example = "PROCESSING")
    OrderCode status,

    @Schema(description = "메뉴 개수", example = "3")
    Integer menuCount,

    @Schema(description = "총 가격", example = "200000")
    Integer totalPrice,

    @Schema(description = "점포 아이디", example = "1")
    Integer storeId,

    @Schema(description = "점포 이름", example = "유경이네 햄버거")
    String storeName,

    @Schema(description = "점포 사진", example = "https://streat-bucket.s3.ap-northeast-2.amazonaws.com/c7f8c1d0-42023.02.26%20%EA%B9%80%EB%AF%BC%EC%A7%80%EB%8B%98%28%ED%95%98%EB%A1%9C%297823.jpg")
    String storeSrc,

    @Schema(description = "대기중인 팀", example = "3")
    Integer waitingTeam,

    @Schema(description = "대기중인 메뉴", example = "6")
    Integer waitingMenu,

    @Schema(description = "상품 리스트")
    List<GetOrderDetailProductListDTO> productList

    ){ }
