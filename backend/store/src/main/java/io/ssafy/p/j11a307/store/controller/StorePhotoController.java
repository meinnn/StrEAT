package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStorePhotoDTO;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StorePhotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/store-photos")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StorePhotoController {

    private final StorePhotoService storePhotoService;

    /**
     * StorePhoto 생성 (이미지 파일 업로드)
     */
    @PostMapping(value = "/photo", consumes = {"multipart/form-data"})
    @Operation(summary = "StorePhoto 생성 (이미지 파일 업로드)")
    public ResponseEntity<MessageResponse> createStorePhoto(
            @RequestHeader("Authorization") String token,  // 첫 번째로 token을 받음
            @RequestPart("images") List<MultipartFile> images){

        // token을 통해 storeId를 조회
        Integer storeId = storePhotoService.getStoreIdByToken(token);

        // 각 이미지를 처리하여 StorePhoto 생성
        for (MultipartFile image : images) {
            // CreateStorePhotoDTO 생성
            CreateStorePhotoDTO createStorePhotoDTO = new CreateStorePhotoDTO(storeId, images);

            // StorePhoto 생성 서비스 호출
            storePhotoService.createStorePhotos(token, images);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("StorePhoto 여러 개 생성 성공"));
    }

    /**
     * StorePhoto 조회 (단일)
     */
    @GetMapping("/photo/{storeId}")
    @Operation(summary = "가게 ID로 해당 가게의 가게대표사진 목록 조회")
    public ResponseEntity<DataResponse<List<ReadStorePhotoDTO>>> getStorePhotosByStoreId(@PathVariable Integer storeId) {
        List<ReadStorePhotoDTO> storePhotos = storePhotoService.getStorePhotosByStoreId(storeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StorePhoto 조회 성공", storePhotos));
    }

    /**
     * StorePhoto 전체 조회
     */
    @GetMapping("/photos")
    @Operation(summary = "StorePhoto 전체 조회")
    public ResponseEntity<DataResponse<List<ReadStorePhotoDTO>>> getAllStorePhotos() {
        List<ReadStorePhotoDTO> storePhotos = storePhotoService.getAllStorePhotos();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StorePhoto 전체 조회 성공", storePhotos));
    }

    /**
     * StorePhoto 수정 (이미지 파일 업로드)
     */
    @PutMapping(value = "/photo/{id}", consumes = {"multipart/form-data"})
    @Operation(summary = "StorePhoto 수정 (이미지 파일 업로드) id는 store-photo-id 입니다.")
    public ResponseEntity<MessageResponse> updateStorePhoto(
            @RequestHeader("Authorization") String token,  // 첫 번째로 token을 받음
            @PathVariable Integer id,
            @RequestPart(value = "images") MultipartFile image) {

        // 이미지 파일이 제공되지 않으면 오류 처리
        if (image == null || image.isEmpty()) {
            throw new BusinessException(ErrorCode.STORE_PHOTO_NULL);
        }

        // StorePhoto 수정 서비스 호출
        storePhotoService.updateStorePhoto(token, id, image);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StorePhoto 수정 성공"));
    }
    /**
     * StorePhoto 삭제
     */
    @DeleteMapping("/photo")
    @Operation(summary = "StorePhoto 삭제")
    public ResponseEntity<MessageResponse> deleteStorePhoto(
            @RequestHeader("Authorization") String token,
            @RequestParam("photoId") Integer photoId) {

        storePhotoService.deleteStorePhotoByToken(token, photoId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StorePhoto 삭제 성공"));
    }
}
