package io.ssafy.p.j11a307.store.service;
import lombok.extern.slf4j.Slf4j;
import io.ssafy.p.j11a307.store.dto.*;
import io.ssafy.p.j11a307.store.entity.*;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService{

    private final StoreRepository storeRepository;
    private final IndustryCategoryRepository industryCategoryRepository;
    private final BusinessDayRepository businessDayRepository;
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

    /**
     * 가게 정보 조회( 사장님이 내 점포 조회 시 나타나는 정보들 ex) 점포 사진, 점포 위치 사진, 영업일...)
     */
    public ReadStoreDetailsDTO getStoreDetailInfoForOwner(String token) {
        Integer userId = ownerClient.getUserId(token, internalRequestKey);  // token으로 userId 조회
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // Store의 사진 목록 가져오기
        List<ReadStorePhotoSrcDTO> storePhotos = storePhotoRepository.findByStoreId(store.getId())
                .stream().map(ReadStorePhotoSrcDTO::new).collect(Collectors.toList());

        List<ReadStoreLocationPhotoSrcDTO> storeLocationPhotos = storeLocationPhotoRepository.findByStoreId(store.getId())
                .stream().map(ReadStoreLocationPhotoSrcDTO::new).collect(Collectors.toList());

        // Product 서비스에서 카테고리 가져오기
        DataResponse<List<String>> categoryResponse = productClient.getProductCategories(store.getId());
        List<String> categories = categoryResponse.getData();

        return new ReadStoreDetailsDTO(store, storePhotos, storeLocationPhotos, categories);
    }

    /**
     * 가게 정보 조회( 손님이 내 점포 조회 시 나타나는 정보들 ex) 점포 위치 사진, 영업상태...)
     */

    public ReadCustomerStoreDTO getStoreDetailInfoForCustomer(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 가게 사진을 리스트로 변환
        List<String> storePhotos = storePhotoRepository.findByStoreId(store.getId())
                .stream().map(storePhoto -> storePhoto.getSrc()).collect(Collectors.toList());

        // 가게 위치 사진 조회, 없으면 가게 대표 사진 사용
        List<String> storeLocationPhotos = storeLocationPhotoRepository.findByStoreId(store.getId())
                .stream().map(storeLocationPhoto -> storeLocationPhoto.getSrc()).collect(Collectors.toList());

        if (storeLocationPhotos.isEmpty()) {
            storeLocationPhotos = new ArrayList<>(storePhotos); // 위치 사진이 없으면 대표 사진 사용
        }

        // 카테고리 정보 가져오기
        DataResponse<List<String>> categoryResponse = productClient.getProductCategories(store.getId());
        List<String> categories = categoryResponse.getData();

        // DTO 반환
        return new ReadCustomerStoreDTO(store, storeLocationPhotos, categories);
    }
    /**
     * 가게 상세 정보 조회( 손님이 내 점포 조회 시 나타나는 상세 정보들 ex) 점포 상세 정보, 영업일)
     */
    @Transactional(readOnly = true)
    public ReadStoreBusinessDayDTO getStoreBusinessDayInfo(Integer storeId) {
        // 가게 정보 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // 가게의 단일 영업일 정보 조회
        BusinessDay businessDay = businessDayRepository.findByStoreId(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_DAY_NOT_FOUND));

        // 가게 정보와 영업일 정보를 포함한 DTO 반환
        return new ReadStoreBusinessDayDTO(store, businessDay);
    }

    /**
     * 가게 사진과 이름 조회
     */
    public ReadStoreBasicInfoDTO getStoreBasicInfo(Integer storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // Store 사진 로직 처리 (첫 번째 사진 선택)
        String src = storePhotoRepository.findByStoreId(storeId)
                .stream().findFirst().map(StorePhoto::getSrc)
                .orElseGet(() -> storeLocationPhotoRepository.findByStoreId(storeId)
                        .stream().findFirst().map(StoreLocationPhoto::getSrc).orElse(null));

        return new ReadStoreBasicInfoDTO(store.getName(), src);
    }

    /**
     * 위도와 경도로 근처 가게 조회
     */
    public List<ReadNearByStoreDTO> getStoresByLocation(double latitude, double longitude, int page, int size) {
        // 페이지네이션을 위한 PageRequest 생성
        Pageable pageable = PageRequest.of(page, size);

        // JPQL로 가까운 가게들을 페이지네이션 처리하여 가져옵니다.
        Page<Store> storePage = storeRepository.findAllByLocationRange(latitude, longitude, pageable);

        // Store 엔티티를 ReadNearByStoreDTO로 변환
        return storePage.stream().map(store -> {
            // Store의 사진 가져오기
            String storePhotoSrc = storePhotoRepository.findByStoreId(store.getId())
                    .stream().findFirst()
                    .map(storePhoto -> new ReadStorePhotoSrcDTO(storePhoto).src())  // 객체 생성 후 src() 호출
                    .orElseGet(() -> storeLocationPhotoRepository.findByStoreId(store.getId())
                            .stream().findFirst().map(storeLocationPhoto -> new ReadStoreLocationPhotoSrcDTO(storeLocationPhoto).src()).orElse(""));

            // Store의 카테고리 가져오기
            DataResponse<List<String>> categoryResponse = productClient.getProductCategories(store.getId());
            List<String> categories = categoryResponse.getData();

            // 거리 계산 (Java에서 Haversine 공식을 적용)
            Integer distance = calculateDistance(latitude, longitude, store.getLatitude(), store.getLongitude());

            // ReadNearByStoreDTO 생성
            return new ReadNearByStoreDTO(
                    store.getName(),
                    storePhotoSrc,
                    store.getStatus(),
                    categories,
                    distance
            );
        }).collect(Collectors.toList());
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

    /**
     * userId를 통해 storeId 조회
     */
    @Transactional(readOnly = true)
    public Integer getStoreIdByUserId(Integer userId) {

        Optional<Store> store = storeRepository.findByUserId(userId);

        if(store.isPresent()) {
            return store.get().getStoreId();
        }
        else throw new BusinessException(ErrorCode.STORE_NOT_FOUND);

    }

    private Integer calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Haversine 공식을 사용하여 두 지점 간의 거리를 계산하는 메서드
        final int R = 6371; // 지구 반경 (단위: km)
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // 거리 (단위: m)

        return (int) distance;
    }
}