package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreStatus;

public record DibsStoreStatusDTO (
        Integer storeId,
        String name,
        String status,
        String imageSrc
) {}
