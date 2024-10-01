package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.ssafy.p.j11a307.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "StoreLocationPhoto 생성 DTO")
public record CreateStoreLocationPhotoDTO(
        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "위도", example = "37.5665")
        String latitude,

        @Schema(description = "경도", example = "126.9780")
        String longitude
) {
    public StoreLocationPhoto toEntity(Store store) {
        return StoreLocationPhoto.builder()
                .store(store)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .createdAt(LocalDateTime.now()) // 생성 시 현재 시간 설정
                .build();
    }
}
