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
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessDayService {

    private final BusinessDayRepository businessDayRepository;
    private final StoreRepository storeRepository;
    private final OwnerClient ownerClient;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    /**
     * BusinessDay 생성
     */
    @Transactional
    public void createBusinessDayByToken(String token, CreateBusinessDayDTO createDTO) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 해당 가게에 이미 영업일이 존재하는지 확인
        boolean exists = businessDayRepository.existsByStoreId(store.getId());
        if (exists) {
            throw new BusinessException(ErrorCode.BUSINESS_DAY_ALREADY_EXISTS); // 이미 영업일이 존재하는 경우 예외 처리
        }

        // DTO를 통해 영업일 생성
        BusinessDay businessDay = createDTO.toEntity(store);
        businessDayRepository.save(businessDay);
    }

    /**
     * BusinessDay 조회 (단건)
     */
    @Transactional(readOnly = true)
    public ReadBusinessDayDTO getBusinessDayByStoreId(Integer storeId) {
        BusinessDay businessDay = businessDayRepository.findByStoreId(storeId)
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
    public void updateBusinessDayByToken(String token, UpdateBusinessDayDTO updateDTO) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // storeId로 BusinessDay 조회
        BusinessDay businessDay = businessDayRepository.findByStoreId(store.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_DAY_NOT_FOUND));

        // DTO를 이용해 BusinessDay 정보 업데이트
        updateDTO.applyTo(businessDay);
        businessDayRepository.save(businessDay);
    }

    /**
     * BusinessDay 삭제
     */
    @Transactional
    public void deleteBusinessDayByToken(String token) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // storeId로 영업일 조회
        BusinessDay businessDay = businessDayRepository.findByStoreId(store.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_DAY_NOT_FOUND));

        // 영업일 삭제
        businessDayRepository.delete(businessDay);
    }
}
