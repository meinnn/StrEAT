package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.ssafy.p.j11a307.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Schema(description = "StoreLocationPhoto 생성 DTO")
public record CreateStoreLocationPhotoDTO(
        @Schema(description = "위도", example = "37.5665")
        @NotEmpty(message = "위도는 필수 입력 항목입니다.")
        Double latitude,

        @Schema(description = "경도", example = "126.9780")
        @NotEmpty(message = "경도는 필수 입력 항목입니다.")
        Double longitude,

        @Schema(description = "이미지 파일", example = "사진")
        @NotNull(message = "이미지 파일은 필수 입력 항목입니다.")
        MultipartFile[] images // 이미지 파일 배열로 처리
) {
    public StoreLocationPhoto toEntity(Store store, String imageUrl) {
        return StoreLocationPhoto.builder()
                .store(store)  // store를 엔티티에 설정
                .latitude(this.latitude)
                .longitude(this.longitude)
                .src(imageUrl)  // S3에서 업로드된 이미지 URL 사용
                .createdAt(LocalDateTime.now())  // 현재 시간 설정
                .build();
    }

    public MultipartFile[] images() {
        return images != null ? images : new MultipartFile[0];  // null일 경우 빈 배열 반환
    }
}
