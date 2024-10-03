package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "StoreLocationPhoto 조회 DTO")
public record ReadStoreLocationPhotoDTO(
        @Schema(description = "사진 ID", example = "1")
        Integer id,

        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "위도", example = "37.5665")
        Double latitude,

        @Schema(description = "경도", example = "126.9780")
        Double longitude,

        @Schema(description = "이미지 경로", example = "https://s3.amazonaws.com/example.jpg")
        String src,  // 이미지 경로 추가

        @Schema(description = "생성일자", example = "2024-09-30T12:34:56")
        LocalDateTime createdAt
) {
    // 엔티티에서 DTO로 변환하는 생성자
    public ReadStoreLocationPhotoDTO(StoreLocationPhoto storeLocationPhoto) {
        this(
                storeLocationPhoto.getId(),
                storeLocationPhoto.getStore().getId(),
                storeLocationPhoto.getLatitude(),
                storeLocationPhoto.getLongitude(),
                storeLocationPhoto.getSrc(),  // 이미지 경로 포함
                storeLocationPhoto.getCreatedAt()
        );
    }
}
