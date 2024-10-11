package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetMyOngoingOrderDetailDTO (
        @Schema(description = "주문 아이디", example = "1")
        Integer ordersId,

        @Schema(description = "주문 상태", example = "PROCESSING")
        OrderCode status,

        @Schema(description = "점포명", example = "민지네 카페")
        String storeName
) { }
