package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetReviewSummaryDTO (
        @Schema(description = "총 리뷰 개수", example = "10")
        Integer reviewTotalCount,

        @Schema(description = "리뷰 평균 점수", example = "3.72")
        Double averageScore
){ }
