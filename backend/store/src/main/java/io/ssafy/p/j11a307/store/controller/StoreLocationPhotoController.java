package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StoreLocationPhotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/store-location-photos")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreLocationPhotoController {

    private final StoreLocationPhotoService storeLocationPhotoService;

    /**
     * StoreLocationPhoto 생성 (이미지 파일 업로드)
     */
    @PostMapping(value = "/location-photo", consumes = {"multipart/form-data"})
    @Operation(summary = "StoreLocationPhoto 생성 (이미지 파일 업로드)")
    public ResponseEntity<MessageResponse> createStoreLocationPhoto(
            @RequestHeader("Authorization") String token,  // 첫 번째로 token을 받음
            @RequestPart("images") MultipartFile[] images,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude) {

        // CreateStoreLocationPhotoDTO 생성
        CreateStoreLocationPhotoDTO createStoreLocationPhotoDTO = new CreateStoreLocationPhotoDTO(latitude, longitude, images);

        // StoreLocationPhoto 생성 서비스 호출 (storeId를 내부에서 조회)
        storeLocationPhotoService.createStoreLocationPhoto(token, createStoreLocationPhotoDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("가게 위치 사진 등록 성공"));
    }


    /**
     * StoreLocationPhoto 조회 (단건)
     */
    @GetMapping("/{storeId}")
    @Operation(summary = "가게 ID로 해당 가게의 위치 사진 목록 조회")
    public ResponseEntity<DataResponse<List<ReadStoreLocationPhotoDTO>>> getStoreLocationPhotosByStoreId(@PathVariable Integer storeId) {
        List<ReadStoreLocationPhotoDTO> storeLocationPhotos = storeLocationPhotoService.getStoreLocationPhotosByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreLocationPhoto 목록 조회 성공", storeLocationPhotos));
    }

    /**
     * StoreLocationPhoto 전체 조회
     */
    @GetMapping("/location-photos")
    @Operation(summary = "StoreLocationPhoto 전체 조회")
    public ResponseEntity<DataResponse<List<ReadStoreLocationPhotoDTO>>> getAllStoreLocationPhotos() {
        List<ReadStoreLocationPhotoDTO> storeLocationPhotos = storeLocationPhotoService.getAllStoreLocationPhotos();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreLocationPhoto 전체 조회 성공", storeLocationPhotos));
    }

    /**
     * StoreLocationPhoto 수정
     */
    @PutMapping(value = "/location-photos/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "StoreLocationPhoto 수정 (이미지 파일 업로드)")
    public ResponseEntity<MessageResponse> updateStoreLocationPhoto(
            @RequestHeader("Authorization") String token,  // 첫 번째로 token을 받음
            @PathVariable Integer id,
            @RequestPart(value = "image", required = false) MultipartFile image,  // 이미지 파일 받기
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude) {

        UpdateStoreLocationPhotoDTO updateDTO = new UpdateStoreLocationPhotoDTO(latitude, longitude, image);

        // 서비스 호출 시 id와 token을 전달하여 수정
        storeLocationPhotoService.updateStoreLocationPhoto(token, id, updateDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreLocationPhoto 수정 성공"));
    }
    /**
     * StoreLocationPhoto 삭제 (token으로 storeId 조회 후 삭제)
     */
    @DeleteMapping("/location-photo")
    @Operation(summary = "StoreLocationPhoto 삭제")
    public ResponseEntity<MessageResponse> deleteStoreLocationPhoto(
            @RequestHeader("Authorization") String token, // token을 받아서 storeId 조회
            @RequestParam("locationPhotoId") Integer locationPhotoId) {

        // token을 사용하여 storeId 조회 후 삭제
        storeLocationPhotoService.deleteStoreLocationPhotoByToken(token, locationPhotoId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreLocationPhoto 삭제 성공"));
    }
}