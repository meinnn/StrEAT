package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.*;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    // 1. 점포 생성
    @PostMapping
    @Operation(summary = "점포 생성")
    public ResponseEntity<MessageResponse> createStore(@RequestHeader("Authorization") String token, @RequestBody CreateStoreDTO storeRequest) {
        storeService.createStore(token, storeRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("가게 생성 성공"));
    }

    // 2. 점포 타입 조회
    @GetMapping("/{id}/type")
    @Operation(summary = "점포 타입(MOBILE/FIXED) 조회")
    public ResponseEntity<DataResponse<String>> getStoreType(@PathVariable Integer id) {
        String storeType = storeService.getStoreType(id);
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("점포 타입 조회 성공", storeType));
    }

    // 3. 점포 정보 조회
    @GetMapping("/{id}")
    @Operation(summary = "점포 정보 조회")
    public ResponseEntity<DataResponse<ReadStoreDTO>> getStoreInfo(@PathVariable Integer id) {
        ReadStoreDTO storeResponse = storeService.getStoreInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 상세 정보 조회 성공", storeResponse));
    }

    // 4. 점포 상세 정보 조회(with photo)
    @GetMapping("/{id}/details")
    @Operation(summary = "점포 상세 정보 조회")
    public ResponseEntity<DataResponse<ReadStoreDetailsDTO>> getStoreDetailInfo(@PathVariable Integer id) {
        ReadStoreDetailsDTO storeDetails = storeService.getStoreDetailInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 상세 정보 조회 성공", storeDetails));
    }

    // 4. 점포 리스트 조회
    @GetMapping("/all")
    @Operation(summary = "점포 리스트 조회")
    public ResponseEntity<DataResponse<List<ReadStoreDTO>>> getAllStores() {
        List<ReadStoreDTO> storeResponses = storeService.getAllStores();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 리스트 조회 성공", storeResponses));
    }

    // 5. 가게 사장님 ID 조회
    @GetMapping("/{id}/ownerId")
    @Operation(summary = "점포 ID를 통해 해당 점포의 사장님 ID 조회")
    public ResponseEntity<DataResponse<Integer>> getOwnerId(@PathVariable Integer id) {
        Integer userId = storeService.getUserIdByStoreId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 사장님 ID 조회 성공", userId));
    }

    // 6. 점포 정보 수정
    @PutMapping("/update")
    @Operation(summary = "점포 정보 수정")
    public ResponseEntity<MessageResponse> updateStore(@RequestHeader("Authorization") String token, @RequestBody UpdateStoreDTO request) {
        storeService.updateStore(token, request);  // token을 통해 점포를 조회하여 수정
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 정보 수정 성공"));
    }

    // 7. 점포 주소 변경
    @PatchMapping("/store/address")
    @Operation(summary = "점포 주소 변경")
    public ResponseEntity<MessageResponse> updateStoreAddress(@RequestHeader("Authorization") String token, @RequestBody String newAddress) {
        storeService.updateStoreAddress(token, newAddress);  // token을 통해 점포 주소 업데이트
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 주소 변경 성공"));
    }

    // 8. 점포 삭제
    @DeleteMapping("/store")
    @Operation(summary = "점포 삭제")
    public ResponseEntity<MessageResponse> deleteStore(@RequestHeader("Authorization") String token) {
        storeService.deleteStoreByToken(token);  // token을 통해 점포 삭제
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 삭제 성공"));
    }

    // 9. 점포 휴무일 변경
    @PatchMapping("/{id}/closedDays")
    @Operation(summary = "점포 휴무일 변경")
    public ResponseEntity<MessageResponse> updateClosedDays(@RequestHeader("Authorization") String token, @RequestBody String closedDays) {
        storeService.updateClosedDays(token, closedDays);  // token을 통해 userId로 점포를 찾고 휴무일 업데이트
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 휴무일 변경 성공"));
    }

    @PatchMapping("/store/ownerWord")
    @Operation(summary = "점포 사장님 한마디 수정")
    public ResponseEntity<MessageResponse> updateOwnerWord(@RequestHeader("Authorization") String token, @RequestBody String ownerWord) {
        storeService.updateOwnerWord(token, ownerWord);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("사장님 한마디 수정 성공"));
    }
}