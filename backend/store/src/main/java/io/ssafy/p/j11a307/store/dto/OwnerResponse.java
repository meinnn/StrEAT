package io.ssafy.p.j11a307.store.dto;

public record OwnerResponse(
        Integer id,
        String name,
        String address,
        String phoneNumber
) {}