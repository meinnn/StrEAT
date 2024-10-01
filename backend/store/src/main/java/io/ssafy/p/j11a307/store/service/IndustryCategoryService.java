package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.CreateIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.ReadIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.dto.UpdateIndustryCategoryDTO;
import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.IndustryCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndustryCategoryService {

    private final IndustryCategoryRepository industryCategoryRepository;

    /**
     * IndustryCategory 생성
     */
    @Transactional
    public void createIndustryCategory(CreateIndustryCategoryDTO createDTO) {
        IndustryCategory industryCategory = IndustryCategory.builder()
                .name(createDTO.name())
                .build();

        industryCategoryRepository.save(industryCategory);
    }

    /**
     * IndustryCategory 조회 (단건)
     */
    @Transactional(readOnly = true)
    public ReadIndustryCategoryDTO getIndustryCategoryById(Integer id) {
        IndustryCategory industryCategory = industryCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND));
        return new ReadIndustryCategoryDTO(industryCategory);
    }

    /**
     * IndustryCategory 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadIndustryCategoryDTO> getAllIndustryCategories() {
        List<IndustryCategory> industryCategories = industryCategoryRepository.findAll();
        return industryCategories.stream()
                .map(ReadIndustryCategoryDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * IndustryCategory 수정
     */
    @Transactional
    public void updateIndustryCategory(Integer id, UpdateIndustryCategoryDTO updateDTO) {
        IndustryCategory industryCategory = industryCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND));

        // 업데이트
        industryCategory.changeName(updateDTO.name());
        industryCategoryRepository.save(industryCategory);
    }

    /**
     * IndustryCategory 삭제
     */
    @Transactional
    public void deleteIndustryCategory(Integer id) {
        IndustryCategory industryCategory = industryCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND));

        industryCategoryRepository.delete(industryCategory);
    }
}
