package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.*;
import java.util.List;

public record ReadStoreDetailsDTO(
        String name,
        String address,
        String ownerWord,
        StoreStatus status,
        List<ReadStorePhotoDTO> storePhotos,
        List<ReadStoreLocationPhotoDTO> storeLocationPhotos,
        BusinessDay businessDay,
        String closedDays,
        List<String> categories  // Product의 카테고리 리스트
) {
    public ReadStoreDetailsDTO(Store store, List<ReadStorePhotoDTO> storePhotos, List<ReadStoreLocationPhotoDTO> storeLocationPhotos, List<String> categories) {
        this(
                store.getName(),
                store.getAddress(),
                store.getOwnerWord(),
                store.getStatus(),
                storePhotos,
                storeLocationPhotos,
                store.getBusinessDay(),
                store.getClosedDays(),
                categories
        );
    }
}
