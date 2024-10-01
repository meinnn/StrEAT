package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.CreateProductDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 1. 상품 등록
    @PostMapping
    @Operation(summary = "상품 등록")
    public ResponseEntity<MessageResponse> createProduct(@RequestBody CreateProductDTO product, @RequestHeader("Authorization") String token) {
        productService.createProduct(product, token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 등록 성공"));
    }

    // 2. 상품 상세 정보 조회
    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 정보 조회")
    public ResponseEntity<DataResponse<ReadProductDTO>> getProductById(@PathVariable Integer productId) {
        ReadProductDTO product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 상세 정보 조회 성공", product));
    }

    // 3. 상품 목록 조회
    @GetMapping
    @Operation(summary = "전체 상품 목록 조회")
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getAllProducts() {
        List<ReadProductDTO> productResponses = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 리스트 조회 성공", productResponses));
    }

    // 4. 가게별 상품 목록 조회 (storeId 기준)
    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게별 상품 목록 조회")
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getProductsByStoreId(@PathVariable Integer storeId) {
        List<ReadProductDTO> productResponses = productService.getProductsByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("가게별 상품 리스트 조회 성공", productResponses));
    }

    // 5. 상품 수정
    @PatchMapping("/{productId}")
    @Operation(summary = "상품 수정")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable Integer productId, @RequestBody UpdateProductDTO productRequest) {
        productService.updateProduct(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 수정 성공"));
    }

    // 6. 상품 삭제
    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 삭제 성공"));
    }

    @GetMapping("/product-names")
    public ResponseEntity<DataResponse<List<String>>> getProductNamesByProductIds(@RequestParam List<Integer> ids) {
        List<String> productNames = productService.getProductNamesByProductIds(ids);  // productOptionService -> productService
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 이름 리스트 조회 성공", productNames));
    }


}
