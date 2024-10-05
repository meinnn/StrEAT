package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.*;

import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.*;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final ProductOptionCategoryService productOptionCategoryService;
    private final ProductPhotoService productPhotoService;
    private final ProductCategoryService productCategoryService;

    // 1. 상품 등록
    @Transactional
    @PostMapping
    @Operation(summary = "상품 등록")
    public ResponseEntity<MessageResponse> createProduct(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateProductDTO product) {

        productService.createProduct(token, product); // token을 전달
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 등록 성공"));
    }

@Transactional
@PostMapping(value = "/all", consumes = {"multipart/form-data"})
@Operation(summary = "상품 관련 전체 정보 등록")
public ResponseEntity<MessageResponse> createProductAll(
        @RequestHeader("Authorization") String token,
        @RequestPart("productInfo") CreateProductAllDTO productDTO,
        @RequestPart("images") List<MultipartFile> images) {

    // 1. 유효성 검증
    validateInput(productDTO, images);

    // 2. 상품 등록 및 상품 ID 반환
    Integer productId = productService.createProduct(token, productDTO.toCreateProductDTO());

    // 3. 상품 이미지 저장
    productPhotoService.createProductPhoto(token, productId, images);

    // 4. 옵션 카테고리 및 옵션 저장
    productDTO.optionCategories().forEach(optionCategory -> {
        // 전달된 productId를 무시하고, 로직에서 고정된 productId 사용
        CreateProductOptionCategoryDTO updatedOptionCategory = optionCategory.withProductId(productId);

        // 1. 상품 옵션 카테고리 먼저 저장하고, 해당 카테고리의 ID를 받음
        Integer productOptionCategoryId = productOptionCategoryService.createProductOptionCategory(updatedOptionCategory);

        // 2. 저장된 상품 옵션 카테고리 ID를 각 상품 옵션에 할당하여 저장
        updatedOptionCategory.productOptions().forEach(option -> {
            // productId와 productOptionCategoryId를 직접 설정
            productOptionService.createProductOption(token, option.withProductIdAndCategoryId(productId, productOptionCategoryId));  // 상품 옵션 저장
        });


    });

    // 성공 메시지 반환
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(MessageResponse.of("상품 등록 성공"));
}

    private void validateInput(CreateProductAllDTO productDTO,
                               List<MultipartFile> images) {
        // 1. 상품 정보 유효성 검사
        if (productDTO.name() == null || productDTO.name().isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_NULL);
        }
        if (productDTO.price() == null || productDTO.price() < 0) {
            throw new BusinessException(ErrorCode.PRODUCT_PRICE_NULL);
        }
        if (productDTO.description() == null || productDTO.description().isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_DESCRIPTION_NULL);
        }

        // 2. 이미지 유효성 검사
        if (images == null || images.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_IMAGES_NULL);
        }
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                throw new BusinessException(ErrorCode.PRODUCT_IMAGES_NULL);
            }
        }

        // 3. 옵션 카테고리 유효성 검사
        if (productDTO.optionCategories() == null || productDTO.optionCategories().isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_EMPTY);
        }
        for (CreateProductOptionCategoryDTO category : productDTO.optionCategories()) {
            if (category.name() == null || category.name().isEmpty()) {
                throw new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NAME_NULL);
            }
            if (category.maxSelect() == null || category.maxSelect() < 0) {
                throw new BusinessException(ErrorCode.INVALID_MAX_SELECT);
            }
            if (category.minSelect() == null || category.minSelect() < 0) {
                throw new BusinessException(ErrorCode.INVALID_MIN_SELECT);
            }
        }

        // 4. 옵션 유효성 검사
        for (CreateProductOptionCategoryDTO category : productDTO.optionCategories()) {
            if (category.productOptions() == null || category.productOptions().isEmpty()) {
                throw new BusinessException(ErrorCode.PRODUCT_OPTION_EMPTY);
            }
            for (CreateProductOptionDTO option : category.productOptions()) {
                if (option.productOptionName() == null || option.productOptionName().isEmpty()) {
                    throw new BusinessException(ErrorCode.PRODUCT_OPTION_NAME_NULL);
                }
                if (option.productOptionPrice() == null || option.productOptionPrice() < 0) {
                    throw new BusinessException(ErrorCode.INVALID_PRICE);
                }
            }
        }

        // 5. 카테고리 유효성 검사
        if (productDTO.categories() == null || productDTO.categories().isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_CATEGORY_EMPTY);
        }
        for (CreateProductCategoryDTO category : productDTO.categories()) {
            if (category.name() == null || category.name().isEmpty()) {
                throw new BusinessException(ErrorCode.PRODUCT_CATEGORY_NAME_NULL);
            }
        }
    }

        // 2. 상품 정보 조회
    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 정보 조회")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<ReadProductDTO>> getProductById(@PathVariable Integer productId) {
        ReadProductDTO product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 상세 정보 조회 성공", product));
    }

    // 3. Store ID로 해당 가게의 상품 카테고리 조회
    @GetMapping("/{storeId}/categories")
    @Operation(summary = "Store ID로 해당 가게의 상품 카테고리 조회")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<List<String>>> getProductCategoriesByStoreId(@PathVariable Integer storeId) {
        List<String> categories = productService.getProductCategoriesByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 카테고리 조회 성공", categories));
    }


    // 4. 상품 목록 조회
    @GetMapping("/all")
    @Operation(summary = "전체 상품 목록 조회")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getAllProducts() {
        List<ReadProductDTO> productResponses = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 리스트 조회 성공", productResponses));
    }

    // 5. 가게별 상품 목록 조회 (storeId 기준)
    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게별 상품 목록 조회")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getProductsByStoreId(@PathVariable Integer storeId) {
        List<ReadProductDTO> productResponses = productService.getProductsByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("가게별 상품 리스트 조회 성공", productResponses));
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "상품 수정")
    @Transactional
    public ResponseEntity<MessageResponse> updateProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer productId,
            @RequestBody UpdateProductDTO productRequest) {

        productService.updateProduct(token, productId, productRequest); // token을 전달
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 수정 성공"));
    }

    // 7. 상품 삭제
    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제")
    @Transactional
    public ResponseEntity<MessageResponse> deleteProduct(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer productId) {

        productService.deleteProduct(token, productId); // token을 전달
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 삭제 성공"));
    }

    // 8. 상품 아이디 리스트로 상품 이름 리스트 조회
    @GetMapping("/product-names")
    @Operation(summary = "상품 아이디 리스트로 상품 이름 리스트 조회")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<List<String>>> getProductNamesByProductIds(@RequestParam List<Integer> ids) {
        List<String> productNames = productService.getProductNamesByProductIds(ids);  // productOptionService -> productService
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 이름 리스트 조회 성공", productNames));
    }

    @PutMapping("/{productId}/stock-status")
    @Operation(summary = "상품 품절 여부 변경 (true : 재고 있음 / false : 품절)")
    @Transactional
    public ResponseEntity<Void> toggleProductStockStatus(@RequestHeader("Authorization") String token,
                                                         @PathVariable Integer productId) {
        // 1. 서비스 호출하여 재고 상태 변경
        productService.toggleProductStockStatus(productId, token);

        // 2. 성공적으로 처리되었으면 204 No Content 응답
        return ResponseEntity.noContent().build();
    }


}
