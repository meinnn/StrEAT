package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StoreLocationPhoto 수정 DTO")
public record UpdateStoreLocationPhotoDTO(
        @Schema(description = "위도", example = "37.5665")
        String latitude,

        @Schema(description = "경도", example = "126.9780")
        String longitude
) {
    // 기존 StoreLocationPhoto 엔티티의 값을 업데이트하는 메서드
    public void applyTo(StoreLocationPhoto storeLocationPhoto) {
        if (latitude != null && !latitude.isEmpty()) {
            storeLocationPhoto.updateLatitude(latitude);
        }
        if (longitude != null && !longitude.isEmpty()) {
            storeLocationPhoto.updateLongitude(longitude);
        }
    }
}
