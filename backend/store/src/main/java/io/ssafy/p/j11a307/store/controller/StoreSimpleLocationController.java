package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.StoreSimpleLocationDTO;
import io.ssafy.p.j11a307.store.entity.StoreSimpleLocation;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StoreSimpleLocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class StoreSimpleLocationController {
    private final StoreSimpleLocationService simpleLocationService;

    /**
     * 간편 위치 저장 생성(가게 정보도 함께 수정)
     */
    @PostMapping(value ="/create", consumes = {"multipart/form-data"})
    @Operation(summary = "간편 위치 저장 생성 with 위치 이미지 파일 업로드")
    public ResponseEntity<MessageResponse> createSimpleLocation(
            @RequestHeader("Authorization") String token,
            @RequestPart StoreSimpleLocationDTO dto,
            @RequestPart("images") List<MultipartFile> images) {
        simpleLocationService.createSimpleLocation(token, dto, images);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("간편 위치 저장 성공"));
    }

    /**
     * 간편 위치 정보만 생성 (가게 정보는 수정하지 않음)
     */
    @PostMapping(value ="/simple-only", consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> createSimpleLocationOnly(
            @RequestHeader("Authorization") String token,
            @RequestPart("locationInfo") StoreSimpleLocationDTO dto,
            @RequestPart("images") List<MultipartFile> images) {

        // 간편 위치 정보만 생성
        simpleLocationService.createSimpleLocationOnly(token, dto, images);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("간편 위치 정보 생성 성공"));
    }

    /**
     * 특정 가게의 간편 위치 목록 조회
     */
    @GetMapping("/store/{storeId}")
    public ResponseEntity<DataResponse<List<StoreSimpleLocation>>> getSimpleLocation(
            @RequestHeader("Authorization") String token) {
        List<StoreSimpleLocation> simpleLocations = simpleLocationService.getSimpleLocation(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("간편 위치 조회 성공", simpleLocations));
    }

    /**
     * 간편 위치 수정
     */
    @PutMapping(value ="/update/{locationId}", consumes = {"multipart/form-data"})
    public ResponseEntity<MessageResponse> updateSimpleLocation(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer locationId,
            @RequestPart StoreSimpleLocationDTO dto,
            @RequestPart("images") List<MultipartFile> images) {

        simpleLocationService.updateSimpleLocation(token, locationId, dto, images);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("간편 위치 및 이미지 수정 성공"));
    }

    /**
     * 간편 위치 삭제
     */
    @DeleteMapping("/delete/{locationId}")
    public ResponseEntity<MessageResponse> deleteSimpleLocation(
            @RequestHeader("Authorization") String token, @PathVariable Integer locationId) {
        simpleLocationService.deleteSimpleLocation(token, locationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("간편 위치 삭제 성공"));
    }
}
