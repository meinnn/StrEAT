package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.*;
import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.repository.IndustryCategoryRepository;
import io.ssafy.p.j11a307.store.repository.StoreLocationPhotoRepository;
import io.ssafy.p.j11a307.store.repository.StorePhotoRepository;
import io.ssafy.p.j11a307.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;

@Service
@RequiredArgsConstructor
public class StoreService{

    private final StoreRepository storeRepository;
    private final IndustryCategoryRepository industryCategoryRepository;
    private final OwnerClient ownerClient;
    private final StoreLocationPhotoRepository storeLocationPhotoRepository;
    private final StorePhotoRepository storePhotoRepository;
    private final ProductClient productClient;

    @Value("${streat.internal-request}")  // "${}"로 수정
    private String internalRequestKey;

    /**
     * 특정 가게의 user ID 반환
     */
    @Transactional(readOnly = true)
    public Integer getUserIdByStoreId(Integer storeId) {
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




    public ReadStoreDetailsDTO getStoreDetailInfo(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // StorePhotos, StoreLocationPhotos 로직 처리
        List<ReadStorePhotoDTO> storePhotos = storePhotoRepository.findByStoreId(storeId)
                .stream().map(ReadStorePhotoDTO::new).collect(Collectors.toList());

        List<ReadStoreLocationPhotoDTO> storeLocationPhotos = storeLocationPhotoRepository.findByStoreId(storeId)
                .stream().map(ReadStoreLocationPhotoDTO::new).collect(Collectors.toList());

        // Product 서비스에서 categories 조회
        List<String> categories = productClient.getProductCategories(storeId);

        return new ReadStoreDetailsDTO(store, storePhotos, storeLocationPhotos, categories);
    }


    /**
     * 가게 생성
     */
    @Transactional
    public void createStore(String token,CreateStoreDTO createStoreDTO) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 점포가 이미 존재하는지 확인
        if (storeRepository.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.STORE_ALREADY_EXISTS);  // 이미 존재하는 경우 예외 처리
        }

        // IndustryCategory 조회
        IndustryCategory industryCategory = industryCategoryRepository
                .findById(createStoreDTO.industryCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND));

        // Store 생성 및 저장
        Store store = createStoreDTO.toEntity(industryCategory);
        store.assignOwner(userId);
        storeRepository.save(store);
    }

    /**
     * 가게 타입 조회 (Enum 타입 처리)
     */
    @Transactional(readOnly = true)
    public String getStoreType(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return store.getType().name();  // Enum 타입의 name() 사용
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
    public void updateStore(String token, UpdateStoreDTO request) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // IndustryCategory 조회
        IndustryCategory industryCategory = industryCategoryRepository
                .findById(request.industryCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NOT_FOUND));

        // Store 업데이트
        Store updatedStore = store.updateWith(request, industryCategory);
        storeRepository.save(updatedStore);
    }

    /**
     * 가게 삭제
     */
    @Transactional
    public void deleteStoreByToken(String token) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId로 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // IndustryCategory와의 연관관계 제거
        store.removeFromCategory();

        // store 삭제
        storeRepository.delete(store);
    }

    /**
     * 가게 주소 업데이트
     */
    @Transactional
    public void updateStoreAddress(String token, String newAddress) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // store 주소 업데이트
        store.changeAddress(newAddress);
        storeRepository.save(store);
    }

    /**
     * 휴무일 업데이트
     */
    @Transactional
    public void updateClosedDays(String token, String closedDays) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId로 점포 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 휴무일 업데이트
        if (closedDays == null || closedDays.isEmpty()) {
            throw new BusinessException(ErrorCode.CLOSED_DAYS_NULL);
        }

        store.changeClosedDays(closedDays);  // Store 엔티티에서 휴무일 변경 메서드 호출
        storeRepository.save(store);  // 변경 사항 저장
    }

    /**
     * 가게 사장님 한마디(ownerWord) 업데이트
     */
    @Transactional
    public void updateOwnerWord(String token, String ownerWord) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId에 해당하는 store 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // ownerWord 업데이트
        if (ownerWord == null || ownerWord.isEmpty()) {
            throw new BusinessException(ErrorCode.OWNER_WORD_NULL);  // 적절한 예외 처리
        }

        store.changeOwnerWord(ownerWord);  // Store 엔티티에서 사장님 한마디 변경 메서드 호출
        storeRepository.save(store);  // 변경 사항 저장
    }
}