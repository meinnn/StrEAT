package io.ssafy.p.j11a307.user.dto;

import lombok.Builder;

@Builder
public record StoreDibsResponse(
        Integer storeId,
        String storeName,
        String status,
        double averageScore,
        boolean alertOn,
        String imageSrc
) {}
