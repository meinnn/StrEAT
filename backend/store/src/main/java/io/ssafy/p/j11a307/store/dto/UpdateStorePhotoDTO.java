package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StorePhoto 수정 DTO")
public record UpdateStorePhotoDTO(
        @Schema(description = "사진 경로", example = "/images/store1_new.jpg")
        String src
) {
    // 주어진 StorePhoto 엔티티의 필드 업데이트
    public void applyTo(StorePhoto storePhoto) {
        if (src != null && !src.isEmpty()) {
            storePhoto.changeSrc(src);
        }
    }
}
