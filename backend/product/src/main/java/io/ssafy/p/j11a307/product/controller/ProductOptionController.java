package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.CreateProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductOptionDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/options")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    // 1. 상품 옵션 생성
    @PostMapping
    @Operation(summary = "상품 옵션 생성")
    public ResponseEntity<MessageResponse> createProductOption(@RequestBody CreateProductOptionDTO createProductOption) {
        productOptionService.createProductOption(createProductOption);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 옵션 등록 성공"));
    }

    // 2. 특정 상품 옵션 조회
    @GetMapping("/{optionId}")
    @Operation(summary = "특정 상품 옵션 조회")
    public ResponseEntity<DataResponse<ReadProductOptionDTO>> getProductOptionById(@PathVariable Integer optionId) {
        ReadProductOptionDTO productOption = productOptionService.getProductOptionById(optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 옵션 조회 성공", productOption));
    }

    // 3. 모든 상품 옵션 조회
    @GetMapping
    @Operation(summary = "모든 상품 옵션 조회")
    public ResponseEntity<DataResponse<List<ReadProductOptionDTO>>> getAllProductOptions() {
        List<ReadProductOptionDTO> productOptions = productOptionService.getAllProductOptions();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 옵션 리스트 조회 성공", productOptions));
    }

    // 4. 상품 옵션 업데이트
    @PutMapping("/{optionId}")
    @Operation(summary = "상품 옵션 업데이트")
    public ResponseEntity<MessageResponse> updateProductOption(@PathVariable Integer optionId, @RequestBody UpdateProductOptionDTO updateProductOption) {
        productOptionService.updateProductOption(optionId, updateProductOption);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 옵션 수정 성공"));
    }

    // 5. 상품 옵션 삭제
    @DeleteMapping("/{optionId}")
    @Operation(summary = "상품 옵션 삭제")
    public ResponseEntity<MessageResponse> deleteProductOption(@PathVariable Integer optionId) {
        productOptionService.deleteProductOption(optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 옵션 삭제 성공"));
    }
}
