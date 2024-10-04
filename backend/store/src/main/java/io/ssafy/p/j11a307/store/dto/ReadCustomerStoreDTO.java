package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreStatus;

import java.util.List;

public record ReadCustomerStoreDTO(
        String name,
        String address,
        String ownerWord,
        StoreStatus status,
        List<String> storePhotos,
        List<String> categories  // Product의 카테고리 리스트
) {
    public ReadCustomerStoreDTO(Store store, List<String> storePhotos, List<String> categories) {
        this(
                store.getName(),
                store.getAddress(),
                store.getOwnerWord(),
                store.getStatus(),
                storePhotos,
                categories
        );
    }
}