package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.CreateProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductOptionDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.ProductOptionService;
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
    public ResponseEntity<MessageResponse> createProductOption(@RequestBody CreateProductOptionDTO createProductOption) {
        productOptionService.createProductOption(createProductOption);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 옵션 등록 성공"));
    }

    // 2. 특정 상품 옵션 조회
    @GetMapping("/{optionId}")
    public ResponseEntity<DataResponse<ReadProductOptionDTO>> getProductOptionById(@PathVariable Integer optionId) {
        ReadProductOptionDTO productOption = productOptionService.getProductOptionById(optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 옵션 조회 성공", productOption));
    }

    // 3. 모든 상품 옵션 조회
    @GetMapping
    public ResponseEntity<DataResponse<List<ReadProductOptionDTO>>> getAllProductOptions() {
        List<ReadProductOptionDTO> productOptions = productOptionService.getAllProductOptions();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 옵션 리스트 조회 성공", productOptions));
    }

    // 4. 상품 옵션 업데이트
    @PutMapping("/{optionId}")
    public ResponseEntity<MessageResponse> updateProductOption(@PathVariable Integer optionId, @RequestBody UpdateProductOptionDTO updateProductOption) {
        productOptionService.updateProductOption(optionId, updateProductOption);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 옵션 수정 성공"));
    }

    // 5. 상품 옵션 삭제
    @DeleteMapping("/{optionId}")
    public ResponseEntity<MessageResponse> deleteProductOption(@PathVariable Integer optionId) {
        productOptionService.deleteProductOption(optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 옵션 삭제 성공"));
    }


    @GetMapping("/product-names")
    public ResponseEntity<DataResponse<List<String>>> getProductNamesByOptionIds(@RequestParam List<Integer> ids) {
        List<String> productNames = productOptionService.getProductNamesByProductOptionIds(ids);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 이름 리스트 조회 성공", productNames));
    }
}
