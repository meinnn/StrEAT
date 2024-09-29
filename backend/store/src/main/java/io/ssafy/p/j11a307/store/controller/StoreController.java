package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.StoreResponse;
import io.ssafy.p.j11a307.store.dto.StoreUpdateRequest;
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
@RequestMapping("/api/stores")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    // 1. 점포 생성
    @PostMapping
    @Operation(summary = "가게 생성")
    public ResponseEntity<MessageResponse> createStore(@RequestBody StoreUpdateRequest storeRequest, @RequestHeader("Authorization") String token) {
        storeService.createStore(storeRequest, token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MessageResponse.of("가게가 성공적으로 생성되었습니다."));
    }

    // 2. 점포 타입 조회
    @GetMapping("/{id}/type")
    @Operation(summary = "점포 타입(이동형/고정형) 조회")
    public ResponseEntity<DataResponse<String>> getStoreType(@PathVariable Integer id) {
        String storeType = storeService.getStoreType(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(DataResponse.of("점포 타입 조회 성공", storeType));
    }

    // 3. 점포 상세 정보 조회
    @GetMapping("/{id}")
    @Operation(summary = "점포 상세 정보 조회")
    public ResponseEntity<DataResponse<StoreResponse>> getStoreInfo(@PathVariable Integer id) {
        StoreResponse storeResponse = storeService.getStoreInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 상세 정보 조회 성공", storeResponse));
    }

    // 4. 점포 리스트 조회
    @GetMapping
    @Operation(summary = "점포 리스트 조회")
    public ResponseEntity<DataResponse<List<StoreResponse>>> getAllStores() {
        List<StoreResponse> storeResponses = storeService.getAllStores();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 리스트 조회 성공", storeResponses));
    }

    // 5. 가게 사장님 ID 조회
    @GetMapping("/{id}/ownerId")
    @Operation(summary = "점포 사장님 ID 조회")
    public ResponseEntity<DataResponse<Integer>> getOwnerId(@PathVariable Integer id) {
        Integer userId = storeService.getOwnerIdByStoreId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 사장님 ID 조회 성공", userId));
    }
    // 6. 점포 정보 수정
    @PutMapping("/{id}")
    @Operation(summary = "점포 정보 수정")
    public ResponseEntity<MessageResponse> updateStore(@PathVariable Integer id, @RequestBody StoreUpdateRequest request) {
        StoreResponse updatedStore = storeService.updateStore(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 정보 수정 성공"));
    }

    // 7. 점포 주소 변경
    @PatchMapping("/{id}/address")
    @Operation(summary = "점포 주소 변경")
    public ResponseEntity<MessageResponse> updateStoreAddress(@PathVariable Integer id, @RequestBody String newAddress) {
        StoreResponse updatedStore = storeService.updateStoreAddress(id, newAddress);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 주소 변경 성공"));
    }

    // 8. 점포 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "점포 삭제 api")
    public ResponseEntity<MessageResponse> deleteStore(@PathVariable Integer id) {
        storeService.deleteStore(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 정보 삭제 성공"));
    }


}
