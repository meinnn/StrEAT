package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.*;

import io.ssafy.p.j11a307.product.entity.ProductOption;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
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

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final ProductOptionCategoryService productOptionCategoryService;
    private final ProductPhotoService productPhotoService;

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
        validateInput(productDTO, images);
        // 2. 상품 등록 및 상품 ID 반환 (단일 카테고리로 처리)
        Integer productId = productService.createProduct(token, productDTO.toCreateProductDTO());
        // 3. 상품 이미지 저장
        productPhotoService.createProductPhoto(token, productId, images);

        // 4. 옵션 카테고리 및 옵션 저장 (null 체크 추가)
        if (productDTO.optionCategories() != null && !productDTO.optionCategories().isEmpty()) {
            productDTO.optionCategories().stream()
                    // 옵션 카테고리 자체가 유효한지 검사
                    .filter(optionCategory -> optionCategory != null && optionCategory.name() != null && !optionCategory.name().isEmpty())
                    .forEach(optionCategory -> {
                        // 전달된 productId를 무시하고, 로직에서 고정된 productId 사용
                        CreateProductOptionCategoryDTO updatedOptionCategory = optionCategory.withProductId(productId);

                        // 1. 상품 옵션 카테고리 먼저 저장하고, 해당 카테고리의 ID를 받음
                        Integer productOptionCategoryId = productOptionCategoryService.createProductOptionCategory(updatedOptionCategory);

                        // 2. 저장된 상품 옵션 카테고리 ID를 각 상품 옵션에 할당하여 저장
                        if (updatedOptionCategory.productOptions() != null && !updatedOptionCategory.productOptions().isEmpty()) {
                            updatedOptionCategory.productOptions().stream()
                                    // 옵션이 유효한지 검사
                                    .filter(option -> option != null && option.productOptionName() != null && !option.productOptionName().isEmpty())
                                    .forEach(option -> {
                                        // productId와 productOptionCategoryId를 직접 설정
                                        productOptionService.createProductOption(token, option.withProductIdAndCategoryId(productId, productOptionCategoryId));  // 상품 옵션 저장
                                    });
                        }
                    });
        }

        // 성공 메시지 반환
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 등록 성공"));
    }


    private void validateInput(CreateProductAllDTO productDTO, List<MultipartFile> images) {
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


        // 3. 옵션 카테고리 및 옵션 유효성 검사 (빈 배열 허용)
        if (productDTO.optionCategories() != null) {
            for (CreateProductOptionCategoryWithoutProductIdDTO category : productDTO.optionCategories()) {
                // 옵션 카테고리 검증
                if (category.name() == null || category.name().isEmpty()) {
                    throw new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NAME_NULL);
                }
                if (category.maxSelect() == null || category.maxSelect() < 0) {
                    throw new BusinessException(ErrorCode.INVALID_MAX_SELECT);
                }
                if (category.minSelect() == null || category.minSelect() < 0) {
                    throw new BusinessException(ErrorCode.INVALID_MIN_SELECT);
                }

                // 옵션 검증 (옵션 리스트가 null이거나 비어 있는 경우 허용)
                if (category.productOptions() != null && !category.productOptions().isEmpty()) {
                    for (CreateProductOptionDTO option : category.productOptions()) {
                        if (option.productOptionName() == null || option.productOptionName().isEmpty()) {
                            throw new BusinessException(ErrorCode.PRODUCT_OPTION_NAME_NULL);
                        }
                        if (option.productOptionPrice() == null || option.productOptionPrice() < 0) {
                            throw new BusinessException(ErrorCode.INVALID_PRICE);
                        }
                    }
                }
            }
        }

        // 4. 카테고리 유효성 검사
        if (productDTO.categoryId() == null) {
            throw new BusinessException(ErrorCode.PRODUCT_CATEGORY_EMPTY);
        }
    }

    private void validateInputs(UpdateProductAllDTO productDTO, List<MultipartFile> images) {
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

        // 2. 이미지 유효성 검사 (업데이트에서는 이미지가 필수는 아님)
        if (images != null) {
            for (MultipartFile image : images) {
                if (image.isEmpty()) {
                    throw new BusinessException(ErrorCode.PRODUCT_IMAGES_NULL);
                }
            }
        }

        // 3. 옵션 카테고리 및 옵션 유효성 검사 (빈 배열 허용)
        if (productDTO.optionCategories() != null) {
            for (UpdateProductOptionCategoryWithoutProductIdDTO category : productDTO.optionCategories()) {
                // 옵션 카테고리 검증
                if (category.name() == null || category.name().isEmpty()) {
                    throw new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NAME_NULL);
                }
                if (category.maxSelect() == null || category.maxSelect() < 0) {
                    throw new BusinessException(ErrorCode.INVALID_MAX_SELECT);
                }
                if (category.minSelect() == null || category.minSelect() < 0) {
                    throw new BusinessException(ErrorCode.INVALID_MIN_SELECT);
                }

                // 옵션 검증 (옵션 리스트가 null이거나 비어 있는 경우 허용)
                if (category.productOptions() != null && !category.productOptions().isEmpty()) {
                    for (UpdateProductOptionDTO option : category.productOptions()) {
                        if (option.productOptionName() == null || option.productOptionName().isEmpty()) {
                            throw new BusinessException(ErrorCode.PRODUCT_OPTION_NAME_NULL);
                        }
                        if (option.productOptionPrice() == null || option.productOptionPrice() < 0) {
                            throw new BusinessException(ErrorCode.INVALID_PRICE);
                        }
                    }
                }
            }
        }

        // 4. 카테고리 유효성 검사
        if (productDTO.categoryId() == null) {
            throw new BusinessException(ErrorCode.PRODUCT_CATEGORY_EMPTY);
        }
    }

    @Transactional
    @PutMapping(value = "/all/{productId}", consumes = {"multipart/form-data"})
    @Operation(summary = "상품 관련 전체 정보 수정")
    public ResponseEntity<MessageResponse> updateProductAll(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer productId,
            @RequestPart("productInfo") UpdateProductAllDTO productDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        // 1. 입력값 검증
        validateInputs(productDTO, images);

        // 2. 상품 정보 업데이트
        productService.updateProducts(token, productId, productDTO);

        // 3. 상품 이미지 업데이트 (새로운 이미지가 전달된 경우에만 업데이트)
        if (images != null && !images.isEmpty()) {
            productPhotoService.updateProductPhotos(token, productId, images);
        }

        // 4. 기존 옵션 카테고리 조회
        List<ProductOptionCategory> existingOptionCategories = productOptionCategoryService.findByProductId(productId);

        // 5. 업데이트할 옵션 카테고리 및 옵션 처리
        if (productDTO.optionCategories() != null) {
            List<Integer> updatedCategoryIds = new ArrayList<>();

            // 업데이트하거나 새로 추가된 옵션 카테고리 처리
            productDTO.optionCategories().forEach(optionCategoryDTO -> {
                Integer productOptionCategoryId = productOptionCategoryService.updateOrCreateProductOptionCategory(
                        productId, optionCategoryDTO);
                updatedCategoryIds.add(productOptionCategoryId);

                // 기존 옵션을 조회하여 업데이트하거나 삭제 처리
                List<ProductOption> existingOptions = productOptionService.findByProductOptionCategoryId(productOptionCategoryId);

                // 업데이트할 옵션 ID 목록
                List<Integer> updatedOptionIds = new ArrayList<>();

                // DTO의 옵션 목록 처리
                if (optionCategoryDTO.productOptions() != null) {
                    optionCategoryDTO.productOptions().forEach(optionDTO -> {
                        Integer optionId = productOptionService.updateOrCreateProductOption(
                                token, productId, productOptionCategoryId, optionDTO);
                        updatedOptionIds.add(optionId);
                    });
                }

                // 삭제할 옵션 처리
                existingOptions.stream()
                        .filter(option -> !updatedOptionIds.contains(option.getId()))
                        .forEach(option -> productOptionService.deleteProductOptions(option.getId()));
            });

            // 삭제할 옵션 카테고리 처리
            existingOptionCategories.stream()
                    .filter(category -> !updatedCategoryIds.contains(category.getId()))
                    .forEach(category -> productOptionCategoryService.deleteProductOptionCategory(category.getId()));
        } else {
            // 옵션 카테고리가 비어있는 경우, 기존의 모든 옵션 카테고리와 옵션 삭제
            existingOptionCategories.forEach(category -> productOptionCategoryService.deleteProductOptionCategory(category.getId()));
        }

        // 성공 메시지 반환
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 수정 성공"));
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


    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게별 상품 목록 조회(카테고리 ID)")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getProductByStoreId(@PathVariable Integer storeId) {
        List<ReadProductDTO> productResponses = productService.getProductByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("가게별 상품 리스트 조회 성공", productResponses));
    }

    // 5. 가게별 상품 목록 조회 (storeId 기준)
    @GetMapping("/store/{storeId}/detail")
    @Operation(summary = "가게별 상품 목록 조회(카테고리 데이터 포함)")
    @Transactional(readOnly = true)
    public ResponseEntity<DataResponse<List<ReadProductAllDTO>>> getProductsByStoreId(@PathVariable Integer storeId) {
        List<ReadProductAllDTO> productResponses = productService.getProductsByStoreId(storeId);
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
    public ResponseEntity<MessageResponse> toggleProductStockStatus(@RequestHeader("Authorization") String token,
                                                         @PathVariable Integer productId) {
        // 1. 서비스 호출하여 재고 상태 변경
        boolean updatedStockStatus = productService.toggleProductStockStatus(productId, token);

        String stockStatusMessage = updatedStockStatus ? "재고 있음" : "품절";

        // 2. 성공적으로 처리되었으면 204 No Content 응답
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 품절 여부 변경 성공 | " + "현재 재고 여부 => " + stockStatusMessage));
    }

}
