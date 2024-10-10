package io.ssafy.p.j11a307.order.dto;

import lombok.Builder;

@Builder
public record SameStoreDTO(
        String storeName,
        String storeType,
        Double storeLat,
        Double storeLon
) {

}
