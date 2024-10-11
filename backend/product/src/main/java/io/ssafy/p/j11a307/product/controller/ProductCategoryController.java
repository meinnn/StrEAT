package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.CreateProductCategoryDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductCategoryDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductCategoryDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.ProductCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    // 1. 상품 카테고리 생성
    @PostMapping
    @Operation(summary = "상품 카테고리 생성")
    public ResponseEntity<MessageResponse> createProductCategory(@RequestHeader("Authorization") String token,
                                                                 @RequestBody CreateProductCategoryDTO createCategoryDTO) {
        productCategoryService.createProductCategory(token, createCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 카테고리 생성 성공"));
    }

    // 2. 상품 카테고리 ID로 조회
    @GetMapping("/{categoryId}")
    @Operation(summary = "상품 카테고리 상세 조회")
    public ResponseEntity<DataResponse<ReadProductCategoryDTO>> getProductCategoryById(@PathVariable Integer categoryId) {
        ReadProductCategoryDTO categoryDTO = productCategoryService.getProductCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 카테고리 조회 성공", categoryDTO));
    }

    // 3. 모든 상품 카테고리 조회
    @GetMapping
    @Operation(summary = "전체 상품 카테고리 목록 조회")
    public ResponseEntity<DataResponse<List<ReadProductCategoryDTO>>> getAllProductCategories() {
        List<ReadProductCategoryDTO> categoryDTOList = productCategoryService.getAllProductCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 카테고리 목록 조회 성공", categoryDTOList));
    }

    // 4. 상품 카테고리 수정
    @PatchMapping("/{categoryId}")
    @Operation(summary = "상품 카테고리 수정")
    public ResponseEntity<MessageResponse> updateProductCategory(@RequestHeader("Authorization") String token,
                                                                 @PathVariable Integer categoryId,
                                                                 @RequestBody UpdateProductCategoryDTO updateCategoryDTO) {
        productCategoryService.updateProductCategory(token, categoryId, updateCategoryDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 카테고리 수정 성공"));
    }


    // 5. 상품 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    @Operation(summary = "상품 카테고리 삭제")
    public ResponseEntity<MessageResponse> deleteProductCategory(@RequestHeader("Authorization") String token,
                                                                 @PathVariable Integer categoryId) {
        productCategoryService.deleteProductCategory(token, categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 카테고리 삭제 성공"));
    }
}
