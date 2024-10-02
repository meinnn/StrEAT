package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "StorePhoto 수정 DTO")
public record UpdateStorePhotoDTO(

        @Schema(description = "이미지 파일", example = "파일")
        MultipartFile image
) {
    // 기존 StorePhoto 엔티티의 값을 업데이트하는 메서드
    public void applyTo(StorePhoto storePhoto, String imageUrl) {
        if (image != null && !image.isEmpty()) {
            storePhoto.changeSrc(imageUrl);
        }
    }

    public UpdateStorePhotoDTO(StorePhoto storePhoto, MultipartFile image) {
        this(image);  // 새로운 이미지 파일이 있으면 추가

    }
}