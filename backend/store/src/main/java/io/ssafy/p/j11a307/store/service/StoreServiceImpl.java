package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.OwnerResponse;
import io.ssafy.p.j11a307.store.dto.StoreResponse;
import io.ssafy.p.j11a307.store.dto.StoreUpdateRequest;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final OwnerClient ownerClient; // Feign Client 주입

    public StoreResponse getStoreWithOwner(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        OwnerResponse owner = ownerClient.getOwnerById(store.getOwnerId()); // Feign Client 사용

        // Store와 Owner 정보를 결합하여 반환
        return new StoreResponse(store, owner);
    }

    @Override
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public String getStoreType(Long id) {
        Store store = storeRepository.findById(id).orElseThrow();
        return store.getType();
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }


    @Override
    public Store updateStore(Long id, StoreUpdateRequest request) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Store not found"));

        // 기존 Store 엔티티를 빌더 패턴으로 업데이트
        Store updatedStore = store.updateWith(request);

        return storeRepository.save(updatedStore);
    }

    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    @Override
    public Store updateStoreAddress(Long id, String newAddress) {
        Store store = storeRepository.findById(id).orElseThrow();
        store.changeAddress(newAddress);
        return storeRepository.save(store);
    }
}
