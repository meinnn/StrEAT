package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Map;

@Builder
public record GetReviewAverageListDTO (
        @Schema(description = "key: <점포 아이디>, value: <별점 평균>")
        Map<Integer, Double> averageReviewList


        ) { }
