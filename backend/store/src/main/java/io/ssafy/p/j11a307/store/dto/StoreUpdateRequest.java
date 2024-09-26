package io.ssafy.p.j11a307.store.dto;

public record StoreUpdateRequest(
        String name,
        String address,
        String latitude,
        String longitude,
        String type,
        String bankAccount,
        String bankName,
        String ownerWord,
        String status
) {}