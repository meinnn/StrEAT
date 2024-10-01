package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.CreateStoreIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.ReadStoreIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.UpdateStoreIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreIndustryCategory;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.IndustryCategoryRepository;
import io.ssafy.p.j11a307.store.repository.StoreIndustryCategoryRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreIndustryCategoryService {

    private final StoreIndustryCategoryRepository storeIndustryCategoryRepository;
    private final StoreRepository storeRepository;
    private final IndustryCategoryRepository industryCategoryRepository;

    /**
     * StoreIndustryCategory 생성
     */
    @Transactional
    public void createStoreIndustryCategory(CreateStoreIndustryCategoryDTO createDTO) {
        // Store 및 IndustryCategory 엔티티 조회
        Store store = storeRepository.findById(createDTO.storeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        IndustryCategory industryCategory = industryCategoryRepository.findById(createDTO.industryCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND));

        // StoreIndustryCategory 엔티티 생성 및 저장
        StoreIndustryCategory storeIndustryCategory = createDTO.toEntity(store, industryCategory);
        storeIndustryCategoryRepository.save(storeIndustryCategory);
    }

    /**
     * StoreIndustryCategory 조회 (단건)
     */
    @Transactional(readOnly = true)
    public ReadStoreIndustryCategoryDTO getStoreIndustryCategoryById(Integer id) {
        StoreIndustryCategory storeIndustryCategory = storeIndustryCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_INDUSTRY_CATEGORY_NOT_FOUND));
        return new ReadStoreIndustryCategoryDTO(storeIndustryCategory);
    }

    /**
     * StoreIndustryCategory 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadStoreIndustryCategoryDTO> getAllStoreIndustryCategories() {
        List<StoreIndustryCategory> storeIndustryCategories = storeIndustryCategoryRepository.findAll();
        return storeIndustryCategories.stream()
                .map(ReadStoreIndustryCategoryDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * StoreIndustryCategory 수정
     */
    @Transactional
    public void updateStoreIndustryCategory(Integer id, UpdateStoreIndustryCategoryDTO updateDTO) {
        StoreIndustryCategory storeIndustryCategory = storeIndustryCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_INDUSTRY_CATEGORY_NOT_FOUND));

        // Store 및 IndustryCategory 엔티티 조회 (필요 시)
        Store store = updateDTO.storeId() != null ?
                storeRepository.findById(updateDTO.storeId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND)) : null;
        IndustryCategory industryCategory = updateDTO.industryCategoryId() != null ?
                industryCategoryRepository.findById(updateDTO.industryCategoryId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND)) : null;

        // StoreIndustryCategory 업데이트
        StoreIndustryCategory updatedEntity = updateDTO.toEntity(storeIndustryCategory, store, industryCategory);
        storeIndustryCategoryRepository.save(updatedEntity);
    }

    /**
     * StoreIndustryCategory 삭제
     */
    @Transactional
    public void deleteStoreIndustryCategory(Integer id) {
        StoreIndustryCategory storeIndustryCategory = storeIndustryCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_INDUSTRY_CATEGORY_NOT_FOUND));
        storeIndustryCategoryRepository.delete(storeIndustryCategory);
    }
}