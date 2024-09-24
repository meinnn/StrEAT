package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.StoreResponse;
import io.ssafy.p.j11a307.store.dto.StoreUpdateRequest;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    // 1. 점포 생성
    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store createdStore = storeService.createStore(store);
        return ResponseEntity.status(201).body(createdStore);
    }

    // 2. 점포 타입 조회
    @GetMapping("/{id}/type")
    public ResponseEntity<String> getStoreType(@PathVariable Long id) {
        String storeType = storeService.getStoreType(id);
        return ResponseEntity.ok(storeType);
    }

    // 3. 점포 리스트 조회
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{id}/with-owner")
    public ResponseEntity<StoreResponse> getStoreWithOwner(@PathVariable Long id) {
        StoreResponse storeResponse = storeService.getStoreWithOwner(id);
        return ResponseEntity.ok(storeResponse);
    }

    // 5. 점포 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody StoreUpdateRequest request) {
        Store updatedStore = storeService.updateStore(id, request);
        return ResponseEntity.ok(updatedStore);
    }

    // 6. 점포 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

    // 7. 점포 주소 변경
    @PatchMapping("/{id}/address")
    public ResponseEntity<Store> updateStoreAddress(@PathVariable Long id, @RequestBody String newAddress) {
        Store updatedStore = storeService.updateStoreAddress(id, newAddress);
        return ResponseEntity.ok(updatedStore);
    }
}
