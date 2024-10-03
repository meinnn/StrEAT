package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StoreLocationPhoto 이미지 경로 DTO")
public record ReadStoreLocationPhotoSrcDTO(
        @Schema(description = "이미지 경로", example = "https://s3.amazonaws.com/example.jpg")
        String src
) {
    // StoreLocationPhoto 엔티티에서 DTO로 변환하는 생성자
    public ReadStoreLocationPhotoSrcDTO(StoreLocationPhoto storeLocationPhoto) {
        this(storeLocationPhoto.getSrc());
    }
}

