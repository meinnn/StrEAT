package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.PayTypeCode;
import io.ssafy.p.j11a307.order.global.StoreStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "검색 요청 정보")
@Builder
public record OrderSearchRequest (
        @Schema(description = "페이지 번호", example = "0")
        Integer pgno,

        @Schema(description = "페이지당 개수", example = "1")
        Integer spp,

        @Schema(description = "시작 기간", example = "2024-10-05T14:55:06.123456")
        LocalDateTime startDate,

        @Schema(description = "끝 기간", example = "2024-10-06T14:55:06.123456")
        LocalDateTime endDate,

        @Schema(description = "주문 상태 태그: REJECTED(\"거절됨\"),\n" +
                "    WAITING_FOR_PROCESSING(\"대기중\"),\n" +
                "    PROCESSING(\"조리중\"),\n" +
                "    WAITING_FOR_RECEIPT(\"수령 대기중\"),\n" +
                "    RECEIVED(\"수령 완료\");", example = "[\"PROCESSING\"]")
        List<String> statusTag, //조리완료, 주문취소 등

        @Schema(description = "결제 방법 태그: CREDIT_CARD(\"신용카드\"),\n" +
                "    CASH(\"현금\"),\n" +
                "    SIMPLE_PAYMENT(\"간편결제\"),\n" +
                "    ACCOUNT_TRANSFER(\"계좌이체\");", example = "[\"CREDIT_CARD\", \"ACCOUNT_TRANSFER\"]")
        List<String> paymentMethodTag //카드결제, 간편결제, 현금결제, 계좌이체
){}
