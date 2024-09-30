package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.CreateProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.ProductOptionCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/option-categories")
@CrossOrigin
@RequiredArgsConstructor
public class ProductOptionCategoryController {

    private final ProductOptionCategoryService productOptionCategoryService;

    // 1. 옵션 카테고리 생성
    @PostMapping
    @Operation(summary = "옵션 카테고리 생성")
    public ResponseEntity<MessageResponse> createProductOptionCategory(@RequestBody CreateProductOptionCategoryDTO createOptionCategoryDTO) {
        productOptionCategoryService.createProductOptionCategory(createOptionCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("옵션 카테고리 생성 성공"));
    }

    // 2. 옵션 카테고리 ID로 조회
    @GetMapping("/{optionCategoryId}")
    @Operation(summary = "옵션 카테고리 상세 조회")
    public ResponseEntity<DataResponse<ReadProductOptionCategoryDTO>> getProductOptionCategoryById(@PathVariable Integer optionCategoryId) {
        ReadProductOptionCategoryDTO optionCategoryDTO = productOptionCategoryService.getProductOptionCategoryById(optionCategoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("옵션 카테고리 조회 성공", optionCategoryDTO));
    }

    // 3. 모든 옵션 카테고리 조회
    @GetMapping
    @Operation(summary = "전체 옵션 카테고리 목록 조회")
    public ResponseEntity<DataResponse<List<ReadProductOptionCategoryDTO>>> getAllProductOptionCategories() {
        List<ReadProductOptionCategoryDTO> optionCategoryDTOList = productOptionCategoryService.getAllProductOptionCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("옵션 카테고리 목록 조회 성공", optionCategoryDTOList));
    }

    // 4. 옵션 카테고리 수정
    @PatchMapping("/{optionCategoryId}")
    @Operation(summary = "옵션 카테고리 수정")
    public ResponseEntity<MessageResponse> updateProductOptionCategory(@PathVariable Integer optionCategoryId,
                                                                       @RequestBody UpdateProductOptionCategoryDTO updateOptionCategoryDTO) {
        productOptionCategoryService.updateProductOptionCategory(optionCategoryId, updateOptionCategoryDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("옵션 카테고리 수정 성공"));
    }

    // 5. 옵션 카테고리 삭제
    @DeleteMapping("/{optionCategoryId}")
    @Operation(summary = "옵션 카테고리 삭제")
    public ResponseEntity<MessageResponse> deleteProductOptionCategory(@PathVariable Integer optionCategoryId) {
        productOptionCategoryService.deleteProductOptionCategory(optionCategoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("옵션 카테고리 삭제 성공"));
    }
}
