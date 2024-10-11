package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.GetStoreSimpleLocationDTO;
import io.ssafy.p.j11a307.store.dto.StoreSimpleLocationDTO;
import io.ssafy.p.j11a307.store.entity.StoreSimpleLocation;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StoreService;
import io.ssafy.p.j11a307.store.service.StoreSimpleLocationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
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
    private final StoreService storeService;

    /**
     * 간편 위치 ID로 가게 정보도 함께 수정
     */
    @PostMapping("/update-location")
    @Operation(summary = "locationId를 이용해 가게 정보 업데이트 / 이동형 가게가 간편 위치 저장 목록에서 영업을 시작할 때")
    public ResponseEntity<MessageResponse> updateStoreLocationFromSimpleLocation(
            @RequestHeader("Authorization") String token,
            @RequestParam Integer storeSimpleLocationId) {

        simpleLocationService.updateStoreLocationFromSimpleLocation(token, storeSimpleLocationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("간편 위치를 이용한 가게 위치 업데이트 성공"));
    }

    /**
     * 간편 위치 정보 생성 (가게 정보는 수정하지 않음)
     */
    @PostMapping(value ="/create", consumes = {"multipart/form-data"})
    @Operation(summary = "간편 위치 정보 생성")
    public ResponseEntity<DataResponse<Integer>> createSimpleLocation(
            @RequestHeader("Authorization") String token,
            @RequestPart("locationInfo") StoreSimpleLocationDTO dto,
            @RequestPart("images") List<MultipartFile> images) {

        // 간편 위치 정보만 생성
        Integer simpleLocationId = simpleLocationService.createSimpleLocationOnly(token, dto, images);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.of("간편 위치 정보 생성 성공", simpleLocationId));
    }

    /**
     * 특정 가게의 간편 위치 목록 조회
     */
    @GetMapping("/store/{storeId}")
    @Operation(summary = "토큰을 통해 내 가게의 간편 위치 목록 조회")
    public ResponseEntity<DataResponse<List<StoreSimpleLocation>>> getSimpleLocation(
            @RequestHeader("Authorization") String token) {
        List<StoreSimpleLocation> simpleLocations = simpleLocationService.getSimpleLocation(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("간편 위치 조회 성공", simpleLocations));
    }

    /**
     * 특정 가게의 간편 위치 조회 -> storeId로 조회
     */
    @GetMapping("/store/{storeId}/current-loc")
    @Operation(summary = "storeId를 통해 내 가게의 현재 간편위치 id 조회")
    public Integer getSelectedSimpleLocationByStoreId (@PathVariable Integer storeId) {
        return simpleLocationService.getSelectedSimpleLocationByStoreId(storeId);
    }

    /*
    간편 위치 상세조회
     */

    @GetMapping("/{id}/info")
    @Operation(summary = "간편위치 id로 간편위치 상세조회")
    public GetStoreSimpleLocationDTO getStoreSimpleLocationInfo(@PathVariable Integer id) {
        return simpleLocationService.getStoreSimpleLocationInfo(id);
    }



        /**
         * 간편 위치 수정
         */
    @PutMapping(value ="/update/{locationId}", consumes = {"multipart/form-data"})
    @Operation(summary = "간편 위치 정보 수정")
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
    @Operation(summary = "간편 위치 정보 삭제")
    public ResponseEntity<MessageResponse> deleteSimpleLocation(
            @RequestHeader("Authorization") String token, @PathVariable Integer locationId) {
        simpleLocationService.deleteSimpleLocation(token, locationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("간편 위치 삭제 성공"));
    }

    /**
     * 리스트 위치 삭제
     */
    @DeleteMapping("/delete/multiple")
    @Operation(summary = "여러 간편 위치 정보 삭제")
    public ResponseEntity<MessageResponse> deleteSimpleLocations(
            @RequestHeader("Authorization") String token, @RequestBody List<Integer> locationIds) {
        simpleLocationService.deleteSimpleLocations(token, locationIds);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("간편 위치 삭제 성공"));
    }
}
