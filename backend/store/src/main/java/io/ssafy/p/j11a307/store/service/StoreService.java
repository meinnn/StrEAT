package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.StoreResponse;
import io.ssafy.p.j11a307.store.dto.StoreUpdateRequest;
import io.ssafy.p.j11a307.store.entity.Store;

import java.util.List;

public interface StoreService {
    Store createStore(Store store);
    String getStoreType(Long id);
    List<Store> getAllStores();
    Store updateStore(Long id, StoreUpdateRequest request);
    void deleteStore(Long id);
    Store updateStoreAddress(Long id, String newAddress);
    StoreResponse getStoreWithOwner(Long id);
}
