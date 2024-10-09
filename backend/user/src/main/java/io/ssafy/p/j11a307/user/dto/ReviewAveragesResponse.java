package io.ssafy.p.j11a307.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public record ReviewAveragesResponse(
        @Schema(description = "key: <점포 아이디>, value: <별점 평균>")
        Map<Integer, Double> averageReviewList
) {}
