package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.CreateStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.ReadStorePhotoDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStorePhotoDTO;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.StorePhotoRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorePhotoService {

    private final StorePhotoRepository storePhotoRepository;
    private final StoreRepository storeRepository;

    /**
     * StorePhoto 생성
     */
    @Transactional
    public void createStorePhoto(CreateStorePhotoDTO createDTO) {
        // Store 엔티티 조회
        Store store = storeRepository.findById(createDTO.storeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StorePhoto 엔티티 생성 및 저장
        StorePhoto storePhoto = createDTO.toEntity(store);
        storePhotoRepository.save(storePhoto);
    }

    /**
     * StorePhoto 조회 (단일)
     */
    @Transactional(readOnly = true)
    public ReadStorePhotoDTO getStorePhotoById(Integer id) {
        StorePhoto storePhoto = storePhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_PHOTO_NOT_FOUND));
        return new ReadStorePhotoDTO(storePhoto);
    }

    /**
     * StorePhoto 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadStorePhotoDTO> getAllStorePhotos() {
        List<StorePhoto> storePhotos = storePhotoRepository.findAll();
        return storePhotos.stream()
                .map(ReadStorePhotoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * StorePhoto 수정
     */
    @Transactional
    public void updateStorePhoto(Integer id, UpdateStorePhotoDTO updateDTO) {
        StorePhoto storePhoto = storePhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_PHOTO_NOT_FOUND));

        // DTO를 이용해 StorePhoto 정보 업데이트
        updateDTO.applyTo(storePhoto);
        storePhotoRepository.save(storePhoto);
    }

    /**
     * StorePhoto 삭제
     */
    @Transactional
    public void deleteStorePhoto(Integer id) {
        StorePhoto storePhoto = storePhotoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_PHOTO_NOT_FOUND));
        storePhotoRepository.delete(storePhoto);
    }
}
