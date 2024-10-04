package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "ProductPhoto 수정 DTO")
public record UpdateProductPhotoDTO(

        @Schema(description = "이미지 파일 목록", example = "파일들")
        List<MultipartFile> images // 복수 이미지 파일
) {
    // 기존 ProductPhoto 엔티티의 값을 업데이트하는 메서드
    public void applyTo(List<ProductPhoto> productPhotos, List<String> imageUrls) {
        if (images != null && !images.isEmpty() && imageUrls.size() == images.size()) {
            for (int i = 0; i < productPhotos.size(); i++) {
                MultipartFile image = images.get(i);
                if (image != null && !image.isEmpty()) {
                    productPhotos.get(i).changeSrc(imageUrls.get(i));
                }
            }
        } else {
            throw new IllegalArgumentException("이미지와 URL의 개수가 일치하지 않습니다.");
        }
    }

    // DTO를 엔티티로 변환하는 생성자
    public UpdateProductPhotoDTO(List<ProductPhoto> productPhotos, List<MultipartFile> images) {
        this(images);  // 새로운 이미지 파일 목록이 있으면 추가
    }
}
