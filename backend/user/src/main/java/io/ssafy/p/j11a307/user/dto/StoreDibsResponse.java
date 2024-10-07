package io.ssafy.p.j11a307.user.dto;

public record StoreDibsResponse(
        Integer storeId,
        String storeName,
        double averageScore,
        boolean alertOn
) {}
