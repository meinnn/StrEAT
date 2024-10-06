package io.ssafy.p.j11a307.product.controller;

import io.ssafy.p.j11a307.product.dto.CreateProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductOptionDTO;
import io.ssafy.p.j11a307.product.global.DataResponse;
import io.ssafy.p.j11a307.product.global.MessageResponse;
import io.ssafy.p.j11a307.product.service.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    // 1. 상품 옵션 생성
    @PostMapping
    @Operation(summary = "상품 옵션 생성")
    public ResponseEntity<MessageResponse> createProductOption(@RequestHeader("Authorization") String token, @RequestBody CreateProductOptionDTO createProductOption) {
        productOptionService.createProductOption(token, createProductOption);
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
    public ResponseEntity<MessageResponse> updateProductOption(@RequestHeader("Authorization") String token, @PathVariable Integer optionId, @RequestBody UpdateProductOptionDTO updateProductOption) {
        productOptionService.updateProductOption(token, optionId, updateProductOption);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 옵션 수정 성공"));
    }

    // 5. 상품 옵션 삭제
    @DeleteMapping("/{optionId}")
    @Operation(summary = "상품 옵션 삭제")
    public ResponseEntity<MessageResponse> deleteProductOption(@RequestHeader("Authorization") String token, @PathVariable Integer optionId) {
        productOptionService.deleteProductOption(token, optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("상품 옵션 삭제 성공"));
    }

    //6. 상품 옵션 가격 합
    @GetMapping("/sum")
    @Operation(summary = "상품 옵션들의 가격 합")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 가격 합 반환")
    })
    @Parameters({
            @Parameter(name = "optionList", description = "가격 더할 옵션 목록")
    })
    @Tag(name = "내부 서비스 간 요청")
    public Integer sumProductOption(@RequestParam List<Integer> optionList, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (optionList != null) return productOptionService.sumProductOption(optionList);

        return 0;
    }

    //7. 옵션 리스트 반환
    @GetMapping("/list")
    @Operation(summary = "옵션 객체 리스트 조회")
    @Tag(name = "내부 서비스 간 요청")
    public List<ReadProductOptionDTO> getProductOptionList(@RequestParam List<Integer> optionList, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (optionList != null) return productOptionService.getProductOptionList(optionList);

        return null;
    }

    //8. 상품 아이디에 따른 옵션들 반환
    @GetMapping("/{productId}/list")
    @Operation(summary = "상품 아이디에 따른 옵션 리스트 조회")
    @Tag(name = "내부 서비스 간 요청")
    public List<ReadProductOptionDTO> getProductOptionListByProductId(@PathVariable Integer productId, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        return productOptionService.getProductOptionListByProductId(productId);
    }
}
