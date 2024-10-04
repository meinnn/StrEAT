package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.ReadProductPhotoDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.service.ProductPhotoService;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product-photos")
@CrossOrigin
@RequiredArgsConstructor
public class ProductPhotoController {

    private final ProductPhotoService productPhotoService;

    /**
     * ProductPhoto 생성 (복수 이미지 파일 업로드)
     */
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @Operation(summary = "ProductPhoto 생성 (복수 이미지 파일 업로드)")
    public ResponseEntity<MessageResponse> createProductPhoto(
            @RequestHeader("Authorization") String token,
            @RequestParam("productId") Integer productId,
            @RequestPart("images") List<MultipartFile> imageFiles) {

        // ProductPhoto 생성 서비스 호출
        productPhotoService.createProductPhoto(token, productId, imageFiles);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("ProductPhoto 생성 성공"));
    }

    /**
     * 특정 상품의 ProductPhoto 전체 조회
     */
    @GetMapping("/{productId}")
    @Operation(summary = "특정 상품의 모든 ProductPhoto 조회")
    public ResponseEntity<DataResponse<List<ReadProductPhotoDTO>>> getProductPhotosByProductId(
            @PathVariable Integer productId) {

        List<ReadProductPhotoDTO> productPhotos = productPhotoService.getProductPhotosByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("해당 상품 전체 사진 조회 성공", productPhotos));
    }

    /**
     * 특정 가게의 전체 ProductPhoto 조회
     */
    @GetMapping("/store/{storeId}")
    @Operation(summary = "특정 가게의 모든 ProductPhoto 조회")
    public ResponseEntity<DataResponse<List<ReadProductPhotoDTO>>> getProductPhotosByStoreId(
            @PathVariable Integer storeId) {

        List<ReadProductPhotoDTO> productPhotos = productPhotoService.getProductPhotosByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("해당 가게 전체 상품 사진 조회 성공", productPhotos));
    }

    /**
     * 전체 ProductPhoto 조회
     */
    @GetMapping("/all")
    @Operation(summary = "전체 ProductPhoto 조회")
    public ResponseEntity<DataResponse<List<ReadProductPhotoDTO>>> getAllProductPhotos() {
        List<ReadProductPhotoDTO> productPhotos = productPhotoService.getAllProductPhotos();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("전체 상품 사진 조회 성공", productPhotos));
    }

    /**
     * ProductPhoto 수정
     */
    @PutMapping("/{productId}/{productPhotoId}")
    @Operation(summary = "ProductPhoto 수정 (복수 이미지 파일 업로드)")
    public ResponseEntity<MessageResponse> updateProductPhoto(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer productId,
            @PathVariable Integer productPhotoId,
            @RequestPart("files") List<MultipartFile> imageFiles) {

        // ProductPhoto 수정 서비스 호출
        productPhotoService.updateProductPhoto(token, productId, productPhotoId, imageFiles);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("Product photos updated successfully."));
    }

    /**
     * ProductPhoto 삭제
     */
    @DeleteMapping("/{productId}/{productPhotoId}")
    @Operation(summary = "ProductPhoto 삭제")
    public ResponseEntity<MessageResponse> deleteProductPhoto(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer productId,
            @PathVariable Integer productPhotoId) {

        productPhotoService.deleteProductPhoto(token, productId, productPhotoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("Product photo deleted successfully."));
    }
}
