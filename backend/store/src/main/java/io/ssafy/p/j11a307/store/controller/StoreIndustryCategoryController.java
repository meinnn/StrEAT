package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateStoreIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.ReadStoreIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStoreIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StoreIndustryCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store-industry-categories")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreIndustryCategoryController {

    private final StoreIndustryCategoryService storeIndustryCategoryService;

    /**
     * StoreIndustryCategory 생성
     */
    @PostMapping
    @Operation(summary = "StoreIndustryCategory 생성")
    public ResponseEntity<MessageResponse> createStoreIndustryCategory(@RequestBody CreateStoreIndustryCategoryDTO createDTO) {
        storeIndustryCategoryService.createStoreIndustryCategory(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("StoreIndustryCategory 생성 성공"));
    }

    /**
     * StoreIndustryCategory 조회 (단건)
     */
    @GetMapping("/{id}")
    @Operation(summary = "StoreIndustryCategory 단일 조회")
    public ResponseEntity<DataResponse<ReadStoreIndustryCategoryDTO>> getStoreIndustryCategoryById(@PathVariable Integer id) {
        ReadStoreIndustryCategoryDTO storeIndustryCategory = storeIndustryCategoryService.getStoreIndustryCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreIndustryCategory 조회 성공", storeIndustryCategory));
    }

    /**
     * StoreIndustryCategory 전체 조회
     */
    @GetMapping
    @Operation(summary = "StoreIndustryCategory 전체 조회")
    public ResponseEntity<DataResponse<List<ReadStoreIndustryCategoryDTO>>> getAllStoreIndustryCategories() {
        List<ReadStoreIndustryCategoryDTO> storeIndustryCategories = storeIndustryCategoryService.getAllStoreIndustryCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreIndustryCategory 전체 조회 성공", storeIndustryCategories));
    }

    /**
     * StoreIndustryCategory 수정
     */
    @PutMapping("/{id}")
    @Operation(summary = "StoreIndustryCategory 수정")
    public ResponseEntity<MessageResponse> updateStoreIndustryCategory(@PathVariable Integer id, @RequestBody UpdateStoreIndustryCategoryDTO updateDTO) {
        storeIndustryCategoryService.updateStoreIndustryCategory(id, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreIndustryCategory 수정 성공"));
    }

    /**
     * StoreIndustryCategory 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "StoreIndustryCategory 삭제")
    public ResponseEntity<MessageResponse> deleteStoreIndustryCategory(@PathVariable Integer id) {
        storeIndustryCategoryService.deleteStoreIndustryCategory(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreIndustryCategory 삭제 성공"));
    }
}
