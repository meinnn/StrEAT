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
    public ResponseEntity<MessageResponse> createProduct(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateProductDTO product) {

        productService.createProduct(token, product); // token을 전달
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("상품 등록 성공"));
    }
    // 2. 상품 정보 조회
    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 정보 조회")
    public ResponseEntity<DataResponse<ReadProductDTO>> getProductById(@PathVariable Integer productId) {
        ReadProductDTO product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 상세 정보 조회 성공", product));
    }

    // 3. Store ID로 해당 가게의 상품 카테고리 조회
    @GetMapping("/{storeId}/categories")
    @Operation(summary = "Store ID로 해당 가게의 상품 카테고리 조회")
    public ResponseEntity<DataResponse<List<String>>> getProductCategoriesByStoreId(@PathVariable Integer storeId) {
        List<String> categories = productService.getProductCategoriesByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 카테고리 조회 성공", categories));
    }


    // 4. 상품 목록 조회
    @GetMapping("/all")
    @Operation(summary = "전체 상품 목록 조회")
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getAllProducts() {
        List<ReadProductDTO> productResponses = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 리스트 조회 성공", productResponses));
    }

    // 5. 가게별 상품 목록 조회 (storeId 기준)
    @GetMapping("/store/{storeId}")
    @Operation(summary = "가게별 상품 목록 조회")
    public ResponseEntity<DataResponse<List<ReadProductDTO>>> getProductsByStoreId(@PathVariable Integer storeId) {
        List<ReadProductDTO> productResponses = productService.getProductsByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("가게별 상품 리스트 조회 성공", productResponses));
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "상품 수정")
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
    public ResponseEntity<DataResponse<List<String>>> getProductNamesByProductIds(@RequestParam List<Integer> ids) {
        List<String> productNames = productService.getProductNamesByProductIds(ids);  // productOptionService -> productService
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("상품 이름 리스트 조회 성공", productNames));
    }


}
