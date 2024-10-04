package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.CreateIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.ReadIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.UpdateIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.IndustryCategoryRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndustryCategoryService {

    private final IndustryCategoryRepository industryCategoryRepository;
    private final StoreRepository storeRepository;
    private final OwnerClient ownerClient;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    /**
     * IndustryCategory 생성
     */
    @Transactional
    public void createIndustryCategory(String token, CreateIndustryCategoryDTO createDTO) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        IndustryCategory industryCategory = IndustryCategory.builder()
                .name(createDTO.name())
                .build();

        industryCategoryRepository.save(industryCategory);
    }

    /**
     * IndustryCategory 조회 (storeId 기반)
     */
    @Transactional(readOnly = true)
    public ReadIndustryCategoryDTO getIndustryCategoryByStoreId(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        IndustryCategory industryCategory = store.getIndustryCategory();
        return new ReadIndustryCategoryDTO(industryCategory);
    }

    /**
     * IndustryCategory 수정
     */
    @Transactional
    public void updateIndustryCategory(String token, UpdateIndustryCategoryDTO updateDTO) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        IndustryCategory industryCategory = store.getIndustryCategory();
        industryCategory.changeName(updateDTO.name());
        industryCategoryRepository.save(industryCategory);
    }

    /**
     * IndustryCategory 삭제
     */
    @Transactional
    public void deleteIndustryCategory(String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        IndustryCategory industryCategory = store.getIndustryCategory();
        industryCategoryRepository.delete(industryCategory);
    }
}