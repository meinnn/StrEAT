package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetDailySalesListDTO(
     /*
    해당 달의 날짜(8월 1일, 2일, 3일..), 해당 날짜에 나간 총 금액 ->  나간 주문내역들: 결제한 사람 이름, 결제금액
     */
        @Schema(description = "고객 이름", example = "김민수")
        String customerName,

        @Schema(description = "결제 금액", example = "120000")
        Integer payAmount
) {
}
