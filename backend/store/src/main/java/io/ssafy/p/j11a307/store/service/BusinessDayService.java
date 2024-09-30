package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.CreateBusinessDayDTO;
import io.ssafy.p.j11a307.store.dto.ReadBusinessDayDTO;
import io.ssafy.p.j11a307.store.dto.UpdateBusinessDayDTO;
import io.ssafy.p.j11a307.store.entity.BusinessDay;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.BusinessDayRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessDayService {

    private final BusinessDayRepository businessDayRepository;
    private final StoreRepository storeRepository;

    /**
     * BusinessDay 생성
     */
    @Transactional
    public void createBusinessDay(CreateBusinessDayDTO createDTO) {
        // Store 엔티티 조회
        Store store = storeRepository.findById(createDTO.storeId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // BusinessDay 엔티티 생성 및 저장
        BusinessDay businessDay = createDTO.toEntity(store);
        businessDayRepository.save(businessDay);
    }

    /**
     * BusinessDay 조회 (단건)
     */
    @Transactional(readOnly = true)
    public ReadBusinessDayDTO getBusinessDayById(Integer id) {
        BusinessDay businessDay = businessDayRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_DAY_NOT_FOUND));
        return new ReadBusinessDayDTO(businessDay);
    }

    /**
     * BusinessDay 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReadBusinessDayDTO> getAllBusinessDays() {
        List<BusinessDay> businessDays = businessDayRepository.findAll();
        return businessDays.stream()
                .map(ReadBusinessDayDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * BusinessDay 수정
     */
    @Transactional
    public void updateBusinessDay(Integer id, UpdateBusinessDayDTO updateDTO) {
        BusinessDay businessDay = businessDayRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_DAY_NOT_FOUND));

        // DTO를 이용해 BusinessDay 정보 업데이트
        updateDTO.applyTo(businessDay);
        businessDayRepository.save(businessDay);
    }

    /**
     * BusinessDay 삭제
     */
    @Transactional
    public void deleteBusinessDay(Integer id) {
        BusinessDay businessDay = businessDayRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_DAY_NOT_FOUND));

        businessDayRepository.delete(businessDay);
    }
}
