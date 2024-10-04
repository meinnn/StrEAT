package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "StoreLocationPhoto 수정 DTO")
public record UpdateStoreLocationPhotoDTO(
        @Schema(description = "위도", example = "37.5665")
        Double latitude,

        @Schema(description = "경도", example = "126.9780")
        Double longitude,

        @Schema(description = "이미지 파일", example = "파일")
        MultipartFile image
) {
    // 기존 StoreLocationPhoto 엔티티의 값을 업데이트하는 메서드
    public void applyTo(StoreLocationPhoto storeLocationPhoto, String imageUrl) {
        if (latitude != null) {
            storeLocationPhoto.updateLatitude(latitude);
        }
        if (longitude != null) {
            storeLocationPhoto.updateLongitude(longitude);
        }
        // 이미지가 있을 경우에만 업데이트
        if (image != null && !image.isEmpty()) {
            storeLocationPhoto.updateSrc(imageUrl);
        }
    }

    public UpdateStoreLocationPhotoDTO(StoreLocationPhoto storeLocationPhoto, MultipartFile image) {
        this(
                storeLocationPhoto.getLatitude(),
                storeLocationPhoto.getLongitude(),
                image  // 새로운 이미지 파일이 있으면 추가
        );
    }
}
