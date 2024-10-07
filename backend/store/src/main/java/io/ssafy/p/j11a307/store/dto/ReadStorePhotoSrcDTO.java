package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StorePhoto 이미지 경로 DTO")
public record ReadStorePhotoSrcDTO(
        @Schema(description = "사진 경로", example = "https://s3.amazonaws.com/example.jpg")
        String src
) {
    // StorePhoto 엔티티에서 DTO로 변환하는 생성자
    public ReadStorePhotoSrcDTO(StorePhoto storePhoto) {
        this(storePhoto.getSrc());
    }
}
