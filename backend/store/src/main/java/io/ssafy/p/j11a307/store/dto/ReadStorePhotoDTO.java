package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "StorePhoto 조회 DTO")
public record ReadStorePhotoDTO(
        @Schema(description = "StorePhoto ID", example = "1")
        Integer id,

        @Schema(description = "Store ID", example = "1")
        Integer storeId,

        @Schema(description = "사진 경로", example = "https://s3.amazonaws.com/example.jpg")
        @Getter
        String src,

        @Schema(description = "생성일자", example = "2024-09-30T12:34:56")
        LocalDateTime createdAt
) {
    // 엔티티에서 DTO로 변환하는 생성자
    public ReadStorePhotoDTO(StorePhoto storePhoto) {
        this(
                storePhoto.getId(),
                storePhoto.getStore().getId(),
                storePhoto.getSrc(),  // 이미지 경로 포함
                storePhoto.getCreatedAt()  // 생성일자 포함
        );
    }
}
