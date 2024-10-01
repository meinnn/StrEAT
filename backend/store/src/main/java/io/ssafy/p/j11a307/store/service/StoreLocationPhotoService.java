package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.CreateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStoreLocationPhotoDTO;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.StoreLocationPhotoRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreLocationPhotoService {

    private final StoreLocationPhotoRepository storeLocationPhotoRepository;
    private final StoreRepository storeRepository;

    /**
     * StoreLocationPhoto 생성
     */
    @Transactional
    public void createStoreLocationPhoto(CreateStoreLocationPhotoDTO createDTO) {
        // Store 엔티티 조회
        Store store = storeRepository.findById(createDTO.storeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StoreLocationPhoto 엔티티 생성 및 저장
        StoreLocationPhoto storeLocationPhoto = createDTO.toEntity(store);
        storeLocationPhotoRepository.save(storeLocationPhoto);
    }

    /**
     * StoreLocationPhoto 조회 (단건)
     */
    @Transactional(readOnly = true)
    public ReadStoreLocationPhotoDTO getStoreLocationPhotoById(Integer id) {
        StoreLocationPhoto storeLocationPhoto = storeLocationPhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_NOT_FOUND));
        return new ReadStoreLocationPhotoDTO(storeLocationPhoto);
    }

    /**
     * StoreLocationPhoto 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadStoreLocationPhotoDTO> getAllStoreLocationPhotos() {
        List<StoreLocationPhoto> storeLocationPhotos = storeLocationPhotoRepository.findAll();
        return storeLocationPhotos.stream()
                .map(ReadStoreLocationPhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * StoreLocationPhoto 수정
     */
    @Transactional
    public void updateStoreLocationPhoto(Integer id, UpdateStoreLocationPhotoDTO updateDTO) {
        StoreLocationPhoto storeLocationPhoto = storeLocationPhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_NOT_FOUND));

        // 위도 및 경도 업데이트
        updateDTO.applyTo(storeLocationPhoto);

        storeLocationPhotoRepository.save(storeLocationPhoto);
    }

    /**
     * StoreLocationPhoto 삭제
     */
    @Transactional
    public void deleteStoreLocationPhoto(Integer id) {
        StoreLocationPhoto storeLocationPhoto = storeLocationPhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_NOT_FOUND));

        storeLocationPhotoRepository.delete(storeLocationPhoto);
    }
}
