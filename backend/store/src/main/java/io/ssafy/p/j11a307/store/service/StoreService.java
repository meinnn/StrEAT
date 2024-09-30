package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.StoreResponse;
import io.ssafy.p.j11a307.store.dto.StoreUpdateRequest;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService{

    private final StoreRepository storeRepository;
    private final OwnerClient ownerClient;

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    @Transactional(readOnly = true)
    public Integer getOwnerIdByStoreId(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return store.getUserId();
    }

    @Transactional(readOnly = true)
    public StoreResponse getStoreInfo(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        return new StoreResponse(store);
    }

    @Transactional
    public StoreResponse createStore(StoreUpdateRequest request, String token) {
        // JWT 토큰에서 userID 추출
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            // 조회된 owner 정보가 없으면 예외 발생
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        Store store = Store.builder()
                .name(request.name())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .type(request.type())
                .bankAccount(request.bankAccount())
                .bankName(request.bankName())
                .ownerWord(request.ownerWord())
                .status(request.status())
                .userId(userId)
                .build();

        Store savedStore = storeRepository.save(store);
        return new StoreResponse(savedStore);
    }

    @Transactional(readOnly = true)
    public String getStoreType(Integer id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return store.getType();
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(StoreResponse::new)  // Store -> StoreResponse 변환
                .collect(Collectors.toList());
    }

    @Transactional
    public StoreResponse updateStore(Integer id, StoreUpdateRequest request) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Store updatedStore = store.updateWith(request);

        return new StoreResponse(storeRepository.save(updatedStore));
    }

    @Transactional
    public void deleteStore(Integer id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        storeRepository.delete(store);
    }

    @Transactional
    public StoreResponse  updateStoreAddress(Integer id, String newAddress) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        store.changeAddress(newAddress);
        return new StoreResponse(storeRepository.save(store));
    }
}
