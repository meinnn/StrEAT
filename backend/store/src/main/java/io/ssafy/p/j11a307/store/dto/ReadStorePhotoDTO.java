package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StorePhoto 조회 DTO")
public record ReadStorePhotoDTO(
        @Schema(description = "StorePhoto ID", example = "1")
        Integer id,

        @Schema(description = "Store ID", example = "1")
        Integer storeId,

        @Schema(description = "사진 경로", example = "/images/store1.jpg")
        String src
) {
    public ReadStorePhotoDTO(StorePhoto storePhoto) {
        this(storePhoto.getId(), storePhoto.getStore().getId(), storePhoto.getSrc());
    }
}
