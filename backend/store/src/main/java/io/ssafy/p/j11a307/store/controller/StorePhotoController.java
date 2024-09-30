package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.CreateStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStorePhotoDTO;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import io.ssafy.p.j11a307.store.service.StorePhotoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store-photos")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StorePhotoController {

    private final StorePhotoService storePhotoService;

    /**
     * StorePhoto 생성
     */
    @PostMapping
    @Operation(summary = "StorePhoto 생성")
    public ResponseEntity<MessageResponse> createStorePhoto(@RequestBody CreateStorePhotoDTO createDTO) {
        storePhotoService.createStorePhoto(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("StorePhoto 생성 성공"));
    }

    /**
     * StorePhoto 조회 (단일)
     */
    @GetMapping("/{id}")
    @Operation(summary = "StorePhoto 단일 조회")
    public ResponseEntity<DataResponse<ReadStorePhotoDTO>> getStorePhotoById(@PathVariable Integer id) {
        ReadStorePhotoDTO storePhoto = storePhotoService.getStorePhotoById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StorePhoto 조회 성공", storePhoto));
    }

    /**
     * StorePhoto 전체 조회
     */
    @GetMapping
    @Operation(summary = "StorePhoto 전체 조회")
    public ResponseEntity<DataResponse<List<ReadStorePhotoDTO>>> getAllStorePhotos() {
        List<ReadStorePhotoDTO> storePhotos = storePhotoService.getAllStorePhotos();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("StorePhoto 전체 조회 성공", storePhotos));
    }

    /**
     * StorePhoto 수정
     */
    @PutMapping("/{id}")
    @Operation(summary = "StorePhoto 수정")
    public ResponseEntity<MessageResponse> updateStorePhoto(@PathVariable Integer id, @RequestBody UpdateStorePhotoDTO updateDTO) {
        storePhotoService.updateStorePhoto(id, updateDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StorePhoto 수정 성공"));
    }

    /**
     * StorePhoto 삭제
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "StorePhoto 삭제")
    public ResponseEntity<MessageResponse> deleteStorePhoto(@PathVariable Integer id) {
        storePhotoService.deleteStorePhoto(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("StorePhoto 삭제 성공"));
    }
}
