package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "StorePhoto 생성 DTO")
public record CreateStorePhotoDTO(
        @Schema(description = "Store ID", example = "1")
        @NotNull(message = "Store ID는 필수 입력 항목입니다.")
        Integer storeId,

        @Schema(description = "이미지 파일", example = "사진")
        @NotNull(message = "이미지 파일은 필수 입력 항목입니다.")
        List<MultipartFile> images // 이미지 파일
) {
    // StorePhoto 엔티티로 변환하는 메서드
    public List<StorePhoto> toEntities(Store store, List<String> imageUrls) {
        // 이미지 파일 리스트를 반복하여 각 이미지를 StorePhoto 엔티티로 변환
        return imageUrls.stream().map(imageUrl -> StorePhoto.builder()
                .store(store)
                .src(imageUrl)  // S3에서 업로드된 이미지 URL 사용
                .createdAt(LocalDateTime.now())  // 현재 시간 설정
                .build()).collect(Collectors.toList());
    }

    // 이미지 파일 반환
    public List<MultipartFile> images() {
        return images;
    }
}
