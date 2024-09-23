package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;

public record StoreResponse(
        Store store,
        OwnerResponse owner
) {}