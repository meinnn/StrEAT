package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOption;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "상품 전체 정보를 생성하기 위한 DTO")
public record CreateProductAllDTO(

//        @Schema(description = "상품명", example = "타코야끼")
//        String name,
//
//        @Schema(description = "가격", example = "2800")
//        Integer price,
//
//        @Schema(description = "상품설명", example = "통통한 문어가 들어있는 타코야끼")
//        String description,

        @Schema(description = "상품 정보")
        @NotNull(message = "상품 정보는 필수 입력 항목입니다.")
        CreateProductDTO productInfo,  // 상품 정보를 포함하는 DTO로 수정

        @Schema(description = "상품 사진 목록")
        @NotNull(message = "상품 사진은 필수 입력 항목입니다.")
        List<MultipartFile> images,

        @Schema(description = "옵션 카테고리 목록")
        List<CreateProductOptionCategoryDTO> productOptionCategories,

        @Schema(description = "옵션 목록")
        List<CreateProductOptionDTO> productOptions,

        @Schema(description = "상품 카테고리 목록")
        List<CreateProductCategoryDTO> productCategories
) {
    // 유효성 검증
//    public CreateProductAllDTO {
//        if (name == null || name.isEmpty()) {
//            throw new BusinessException(ErrorCode.PRODUCT_NAME_NULL);
//        }
//        if (price == null || price < 0) {
//            throw new BusinessException(ErrorCode.INVALID_PRICE);
//        }
//        if (description == null || description.isEmpty()) {
//            throw new BusinessException(ErrorCode.PRODUCT_DESCRIPTION_NULL);
//        }
//        if (images == null || images.isEmpty()) {
//            throw new BusinessException(ErrorCode.PRODUCT_PHOTO_NULL);
//        }
//    }
//
//    // Product 엔티티로 변환하는 메서드
//    public Product toProductEntity() {
//        return Product.builder()
//                .name(this.name)
//                .price(this.price)
//                .description(this.description)
//                .build();
//    }
//
//    // ProductPhoto 엔티티 목록으로 변환하는 메서드
//    public List<ProductPhoto> toProductPhotosEntity(Product product, List<String> imageUrls) {
//        if (imageUrls.size() != images.size()) {
//            throw new IllegalArgumentException("이미지 파일과 URL 개수가 일치하지 않습니다.");
//        }
//
//        return imageUrls.stream().map(imageUrl -> ProductPhoto.builder()
//                .product(product)
//                .src(imageUrl)
//                .build()
//        ).toList();
//    }
//
//    // ProductOptionCategory 엔티티 목록으로 변환하는 메서드
//    public List<ProductOptionCategory> toProductOptionCategoriesEntity(Product product) {
//        return productOptionCategories.stream()
//                .map(optionCategory -> optionCategory.toEntity(product)) // DTO에서 엔티티로 변환
//                .toList();
//    }
//
//    // ProductOption 엔티티 목록으로 변환하는 메서드
//    public List<ProductOption> toProductOptionsEntity(Product product, List<ProductOptionCategory> productOptionCategories) {
//        return productOptions.stream()
//                .map(option -> {
//                    // 해당 옵션의 카테고리를 찾는 로직 추가
//                    ProductOptionCategory category = productOptionCategories.stream()
//                            .filter(c -> c.getId().equals(option.productOptionCategoryId()))
//                            .findFirst()
//                            .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));
//
//                    return option.toEntity(product, category);
//                }).toList();
//    }

    public CreateProductAllDTO {
        if (productInfo == null) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_NULL);
        }
        if (images == null || images.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_PHOTO_NULL);
        }
    }

    // Product 엔티티로 변환하는 메서드
    public Product toProductEntity() {
        return productInfo.toEntity(); // CreateProductDTO의 toEntity 메서드 사용
    }

    // ProductPhoto 엔티티 목록으로 변환하는 메서드
    public List<ProductPhoto> toProductPhotosEntity(Product product, List<String> imageUrls) {
        if (imageUrls.size() != images.size()) {
            throw new IllegalArgumentException("이미지 파일과 URL 개수가 일치하지 않습니다.");
        }

        return imageUrls.stream().map(imageUrl -> ProductPhoto.builder()
                .product(product)
                .src(imageUrl)
                .build()
        ).toList();
    }

    // ProductOptionCategory 엔티티 목록으로 변환하는 메서드
    public List<ProductOptionCategory> toProductOptionCategoriesEntity(Product product) {
        return productOptionCategories.stream()
                .map(optionCategory -> optionCategory.toEntity(product)) // DTO에서 엔티티로 변환
                .toList();
    }

    // ProductOption 엔티티 목록으로 변환하는 메서드
    public List<ProductOption> toProductOptionsEntity(Product product, List<ProductOptionCategory> productOptionCategories) {
        return productOptions.stream()
                .map(option -> {
                    // 해당 옵션의 카테고리를 찾는 로직 추가
                    ProductOptionCategory category = productOptionCategories.stream()
                            .filter(c -> c.getId().equals(option.productOptionCategoryId()))
                            .findFirst()
                            .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

                    return option.toEntity(product, category);
                }).toList();
    }
}