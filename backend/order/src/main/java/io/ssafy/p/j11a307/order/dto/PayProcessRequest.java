package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PayProcessRequest (
        @Schema(description = "성공 여부", example = "1")
        Integer isSuccess,

        @Schema(description = "주문번호", example = "A317DCD")
        String orderNumber,

        @Schema(description = "결제 시각", example = "2024-10-08T18:53:09+09:00")
        String approvedAt,

        @Schema(description = "결제 방식", example = "간편결제")
        String method
){ }
