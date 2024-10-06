package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "ProductPhoto 생성 DTO")
public record CreateProductPhotoDTO(
        @Schema(description = "Product ID", example = "1")
        @NotNull(message = "Product ID는 필수 입력 항목입니다.")
        Integer ProductId,

        @Schema(description = "이미지 파일 목록", example = "사진들")
        @NotNull(message = "이미지 파일은 필수 입력 항목입니다.")
        List<MultipartFile> images // 이미지 파일 목록
) {
    // 여러 개의 ProductPhoto 엔티티로 변환하는 메서드
    public List<ProductPhoto> toEntity(Product product, List<String> imageUrls) {
        if (imageUrls.size() != images.size()) {
            throw new IllegalArgumentException("이미지 파일과 URL 개수가 일치하지 않습니다.");
        }

        return imageUrls.stream().map(imageUrl -> ProductPhoto.builder()
                .product(product)
                .src(imageUrl)  // S3에서 업로드된 이미지 URL 사용
                .createdAt(LocalDateTime.now())  // 현재 시간 설정
                .build()
        ).toList();
    }

    // 이미지 파일 목록 반환
    public List<MultipartFile> images() {
        return images;
    }
}