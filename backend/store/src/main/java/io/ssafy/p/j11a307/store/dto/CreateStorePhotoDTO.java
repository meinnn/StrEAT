package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.ssafy.p.j11a307.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StorePhoto 생성 DTO")
public record CreateStorePhotoDTO(
        @Schema(description = "Store ID", example = "1")
        Integer storeId,

        @Schema(description = "사진 경로", example = "/images/store1.jpg")
        String src
) {
    // DTO를 StorePhoto 엔티티로 변환하는 메서드
    public StorePhoto toEntity(Store store) {
        return StorePhoto.builder()
                .store(store)
                .src(this.src)
                .build();
    }
}
