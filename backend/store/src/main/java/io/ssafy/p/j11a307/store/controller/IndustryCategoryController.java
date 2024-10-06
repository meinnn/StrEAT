package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.ReadIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.UpdateIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.IndustryCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/industry-categories")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class IndustryCategoryController {

    private final IndustryCategoryService industryCategoryService;

    /**
     * IndustryCategory 생성
     */
    @PostMapping
    @Operation(summary = "IndustryCategory 생성")
    public ResponseEntity<MessageResponse> createIndustryCategory(@RequestHeader("Authorization") String token, @RequestBody CreateIndustryCategoryDTO createDTO) {
        industryCategoryService.createIndustryCategory(token, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("IndustryCategory 생성 성공"));
    }

    /**
     * IndustryCategory 조회 (storeId 기반)
     */
    @GetMapping("/{storeId}")
    @Operation(summary = "storeId를 통한 IndustryCategory 조회")
    public ResponseEntity<DataResponse<ReadIndustryCategoryDTO>> getIndustryCategoryByStoreId(@PathVariable Integer storeId) {
        ReadIndustryCategoryDTO industryCategory = industryCategoryService.getIndustryCategoryByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("IndustryCategory 조회 성공", industryCategory));
    }

    /**
     * IndustryCategory 수정
     */
    @PutMapping
    @Operation(summary = "IndustryCategory 수정")
    public ResponseEntity<MessageResponse> updateIndustryCategory(@RequestHeader("Authorization") String token, @RequestBody UpdateIndustryCategoryDTO updateDTO) {
        industryCategoryService.updateIndustryCategory(token, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("IndustryCategory 수정 성공"));
    }

    /**
     * IndustryCategory 삭제
     */
    @DeleteMapping
    @Operation(summary = "IndustryCategory 삭제")
    public ResponseEntity<MessageResponse> deleteIndustryCategory(@RequestHeader("Authorization") String token) {
        industryCategoryService.deleteIndustryCategory(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("IndustryCategory 삭제 성공"));
    }
}
