package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.*;
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

    @Value("${streat.internal-request}")  // "${}"로 수정
    private String internalRequestKey;

    /**
     * 특정 가게의 owner ID 반환
     */
    @Transactional(readOnly = true)
    public Integer getOwnerIdByStoreId(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return store.getUserId();
    }

    /**
     * 가게 정보 조회
     */
    @Transactional(readOnly = true)
    public ReadStoreDTO getStoreInfo(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return new ReadStoreDTO(store);  // ReadStoreDTO로 변환하여 반환
    }

    /**
     * 가게 생성
     */
    @Transactional
    public void createStore(CreateStoreDTO createStoreDTO, String token) {
        // JWT 토큰에서 userID 추출
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // CreateStoreDTO에서 Store 엔티티로 변환
        Store store = createStoreDTO.toEntity();
        store.assignOwner(userId);

        storeRepository.save(store);
    }

    /**
     * 가게 타입 조회
     */
    @Transactional(readOnly = true)
    public String getStoreType(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return store.getType();
    }

    /**
     * 모든 가게 정보 조회
     */
    @Transactional(readOnly = true)
    public List<ReadStoreDTO> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(ReadStoreDTO::new)  // 각 Store 엔티티를 ReadStoreDTO로 변환
                .collect(Collectors.toList());
    }

    /**
     * 가게 정보 업데이트
     */
    @Transactional
    public void updateStore(Integer id, UpdateStoreDTO request) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Store updatedStore = store.updateWith(request);
        storeRepository.save(updatedStore);
    }

    /**
     * 가게 삭제
     */
    @Transactional
    public void deleteStore(Integer id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        storeRepository.delete(store);
    }

    /**
     * 가게 주소 업데이트
     */
    @Transactional
    public void updateStoreAddress(Integer storeId, String newAddress) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        store.changeAddress(newAddress);
        storeRepository.save(store);
    }
}