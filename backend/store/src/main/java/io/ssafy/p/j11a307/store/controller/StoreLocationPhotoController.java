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
    @PostMapping(value = "/{storeId}/location-photo", consumes = {"multipart/form-data"})
    @Operation(summary = "StoreLocationPhoto 생성 (이미지 파일 업로드)")
    public ResponseEntity<MessageResponse> createStoreLocationPhoto(
            @PathVariable Integer storeId,
            @RequestPart("images") MultipartFile[] images,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestHeader("Authorization") String token) {

        // CreateStoreLocationPhotoDTO 생성
        CreateStoreLocationPhotoDTO createStoreLocationPhotoDTO = new CreateStoreLocationPhotoDTO(storeId, latitude, longitude, images);

        storeLocationPhotoService.createStoreLocationPhoto(storeId, createStoreLocationPhotoDTO, token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("가게 위치 사진 등록 성공"));
    }


    /**
     * StoreLocationPhoto 조회 (단건)
     */
    @GetMapping("/location-photos/{id}")
    @Operation(summary = "StoreLocationPhoto 단일 조회")
    public ResponseEntity<DataResponse<ReadStoreLocationPhotoDTO>> getStoreLocationPhotoById(@PathVariable Integer id) {
        ReadStoreLocationPhotoDTO storeLocationPhoto = storeLocationPhotoService.getStoreLocationPhotoById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StoreLocationPhoto 조회 성공", storeLocationPhoto));
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
            @PathVariable Integer id,
            @RequestPart(value = "image", required = false) MultipartFile image, // 이미지 파일 받기
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude) {

        UpdateStoreLocationPhotoDTO updateDTO = new UpdateStoreLocationPhotoDTO(latitude, longitude, image);

        // 서비스 호출 시 id를 별도로 전달
        storeLocationPhotoService.updateStoreLocationPhoto(id, updateDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreLocationPhoto 수정 성공"));
    }
    /**
     * StoreLocationPhoto 삭제
     */
    @DeleteMapping("/location-photos/{id}")
    @Operation(summary = "StoreLocationPhoto 삭제")
    public ResponseEntity<MessageResponse> deleteStoreLocationPhoto(@PathVariable Integer id) {
        storeLocationPhotoService.deleteStoreLocationPhoto(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StoreLocationPhoto 삭제 성공"));
    }
}