package io.ssafy.p.j11a307.user.dto;

public record DibsStoreStatusResponse(
        Integer storeId,
        String name,
        String status,
        String imageSrc
) {}
