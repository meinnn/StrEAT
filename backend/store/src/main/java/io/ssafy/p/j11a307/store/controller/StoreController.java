package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.*;
import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.ssafy.p.j11a307.store.global.DataResponse;
import io.ssafy.p.j11a307.store.global.PagedDataResponse;
import io.ssafy.p.j11a307.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.ssafy.p.j11a307.store.global.MessageResponse;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    /**
     * 점포 상태 변경
     */
    @PatchMapping("/{storeId}/status")
    @Operation(summary = "점포 상태 변경(영업중 -> 영업종료)")
    public ResponseEntity<MessageResponse> updateStoreStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam StoreStatus status) {
        storeService.updateStoreStatus(token, status);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("Store 상태 업데이트 성공"));
    }


    // 1. 점포 생성
    @PostMapping
    @Operation(summary = "점포 생성")
    public ResponseEntity<DataResponse<Integer>> createStore(@RequestHeader("Authorization") String token, @RequestBody CreateStoreDTO storeRequest) {
        Integer storeId = storeService.createStore(token, storeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(DataResponse.of("가게 생성 성공", storeId));
    }

    // 2. userId에 해당하는 storeId 반환
    @GetMapping("/user/{userId}")
    public ResponseEntity<Integer> getStoreIdByUserId(@PathVariable Integer userId) {

        Integer storeId = storeService.getStoreIdByUserId(userId);
        return ResponseEntity.ok(storeId);
    }

    /**
     * 가게 ID 리스트로 가게 정보 조회
     */
    @GetMapping("/dibs")
    @Operation(summary = "찜한 가게 조회용 - 가게 ID 리스트로 가게 정보 조회(가게 ID, 가게 이름, 영업 상태)")
    public ResponseEntity<List<DibsStoreStatusDTO>> getStoreStatusByIds(
            @RequestParam List<Integer> storeIds) {
        // 주어진 가게 ID 리스트에 해당하는 가게 정보 조회
        List<DibsStoreStatusDTO> storeStatusList = storeService.getStoresByIds(storeIds);
        return ResponseEntity.ok(storeStatusList);
    }


    // 2. 점포 타입 조회
    @GetMapping("/{id}/type")
    @Operation(summary = "점포 타입(MOBILE/FIXED) 조회")
    public ResponseEntity<DataResponse<String>> getStoreType(@PathVariable Integer id) {
        String storeType = storeService.getStoreType(id);
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("점포 타입 조회 성공", storeType));
    }

    // 3. 점포 정보 조회
    @GetMapping("/{id}")
    @Operation(summary = "점포 정보 조회")
    public ResponseEntity<DataResponse<ReadStoreDTO>> getStoreInfo(@PathVariable Integer id) {
        ReadStoreDTO storeResponse = storeService.getStoreInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 상세 정보 조회 성공", storeResponse));
    }

    // 점포 정보 조회 (손님 - 점포 상세 정보 조회)
    @GetMapping("/{id}/business-day")
    @Operation(summary = "손님 - 점포 상세 정보 조회 (영업일 포함)")
    public ResponseEntity<DataResponse<ReadStoreBusinessDayDTO>> getStoreBusinessDayInfo(@PathVariable Integer id) {
        ReadStoreBusinessDayDTO storeResponse = storeService.getStoreBusinessDayInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 영업일 정보 조회 성공", storeResponse));
    }

    // 새로운 점포 상세 정보 조회 컨트롤러
    @GetMapping("{id}/details/customer")
    @Operation(summary = "손님 - 간단한 점포 정보 조회 (상호명, 영업상태, 가게 주소, 음식 카테고리, 가게 사진)")
    public ResponseEntity<DataResponse<ReadCustomerStoreDTO>> getStoreDetailInfoForCustomer(@PathVariable Integer id) {
        ReadCustomerStoreDTO storeDetails = storeService.getStoreDetailInfoForCustomer(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("간단한 점포 정보 조회 성공", storeDetails));
    }

    // 4. 점포 상세 정보 조회(사장님 - 점포 상세 정보 조회)
    @GetMapping("/details/owner")
    @Operation(summary = "사장님 - 점포 상세 정보 조회 (상호명, 영업상태, 가게 주소, 사장님 한마디, 음식 카테고리, 가게 사진, 영업 위치 사진, 영업일, 휴무일)")
    public ResponseEntity<DataResponse<ReadStoreDetailsDTO>> getStoreDetailInfoForOwner(@RequestHeader("Authorization") String token) {
        ReadStoreDetailsDTO storeDetails = storeService.getStoreDetailInfoForOwner(token);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 상세 정보 조회 성공", storeDetails));
    }


    // 5. 점포 기본 정보 조회(리뷰 조회시 storeId로 검색했을 떄 점포 사진과 점포이름 반환)
    @GetMapping("/{id}/photo-name")
    @Operation(summary = "점포 기본 정보 조회 (가게 이름, 점포 사진(점포 사진이 null이면 위치 사진으로 대체))")
    public ResponseEntity<DataResponse<ReadStoreBasicInfoDTO>> getStoreBasicInfo(@PathVariable Integer id) {
        ReadStoreBasicInfoDTO storeBasicInfo = storeService.getStoreBasicInfo(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 기본 정보 조회 성공", storeBasicInfo));
    }

    // 4. 점포 리스트 조회
    @GetMapping("/all")
    @Operation(summary = "점포 리스트 조회")
    public ResponseEntity<DataResponse<List<ReadStoreDTO>>> getAllStores() {
        List<ReadStoreDTO> storeResponses = storeService.getAllStores();
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 리스트 조회 성공", storeResponses));
    }


    // 5. 가게 사장님 ID 조회
    @GetMapping("/{id}/ownerId")
    @Operation(summary = "점포 ID를 통해 해당 점포의 사장님 ID 조회")
    public ResponseEntity<DataResponse<Integer>> getOwnerId(@PathVariable Integer id) {
        Integer userId = storeService.getUserIdByStoreId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("점포 사장님 ID 조회 성공", userId));
    }

    @GetMapping("/nearby")
    @Operation(summary = "위도와 경도로 근처 가게 20개씩 페이지네이션 조회")
    public ResponseEntity<PagedDataResponse<List<ReadNearByStoreDTO>>> getNearbyStores(
            @RequestParam
            @Parameter(description = "위도", example = "37.123456") double latitude,
            @RequestParam
            @Parameter(description = "경도", example = "127.123456") double longitude,
            @RequestParam(defaultValue = "0")
            @Parameter(description = "페이지 번호", example = "0") int page,
            @RequestParam(defaultValue = "20")
            @Parameter(description = "페이지 크기", example = "20") int size) {

        // 페이징된 결과를 Page 객체로 받음
        Page<ReadNearByStoreDTO> storePage = storeService.getStoresByLocation(latitude, longitude, page, size);

        // Page 객체에서 List로 변환
        List<ReadNearByStoreDTO> storesList = storePage.getContent();

        // lastPage는 총 페이지 수에서 1을 뺀 값으로 설정
        int lastPage = Math.max(storePage.getTotalPages() - 1, 0);
        PagedDataResponse<List<ReadNearByStoreDTO>> response = PagedDataResponse.of(
                "20개씩 가게 리스트 가져오기 성공",
                storesList,
                storePage.getTotalElements(),
                lastPage,
                storePage.getNumber()
        );

        return ResponseEntity.ok(response);
    }


    /**
     * 위도와 경도로 1km 반경 내 근처 가게 20개씩 페이지네이션 조회
     */
    @GetMapping("/nearby/1km")
    @Operation(summary = "위도와 경도로 1km 반경 내 근처 가게 조회")
    public ResponseEntity<PagedDataResponse<List<ReadNearByStoreDTO>>> getNearbyStoresWithin1KM(
            @RequestParam
            @Parameter(description = "위도", example = "37.123456") double latitude,
            @RequestParam
            @Parameter(description = "경도", example = "127.123456") double longitude,
            @RequestParam(defaultValue = "0")
            @Parameter(description = "페이지 번호", example = "0") int page,
            @RequestParam(defaultValue = "20")
            @Parameter(description = "페이지 크기", example = "20") int size) {

        // 1km 반경 내의 가게들 조회
        Page<ReadNearByStoreDTO> storePage = storeService.getStoresWithin1KM(latitude, longitude, page, size);
        List<ReadNearByStoreDTO> storesList = storePage.getContent();
        // lastPage는 총 페이지 수에서 1을 뺀 값으로 설정
        int lastPage = Math.max(storePage.getTotalPages() - 1, 0);
        PagedDataResponse<List<ReadNearByStoreDTO>> response = PagedDataResponse.of(
                "20개씩 가게 리스트 가져오기 성공",
                storesList,
                storePage.getTotalElements(),
                lastPage,
                storePage.getNumber()
        );

        return ResponseEntity.ok(response);
    }

    // 6. 점포 정보 수정
    @PutMapping("/update")
    @Operation(summary = "점포 정보 수정")
    public ResponseEntity<MessageResponse> updateStore(@RequestHeader("Authorization") String token, @RequestBody UpdateStoreDTO request) {
        storeService.updateStore(token, request);  // token을 통해 점포를 조회하여 수정
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 정보 수정 성공"));
    }

    // 7. 점포 주소 변경
    @PatchMapping("/store/address")
    @Operation(summary = "점포 주소 변경")
    public ResponseEntity<MessageResponse> updateStoreAddress(@RequestHeader("Authorization") String token, @RequestBody UpdateAddressDTO updateAddressDTO) {
        storeService.updateStoreAddress(token, updateAddressDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 주소 변경 성공"));
    }

    // 8. 점포 삭제
    @DeleteMapping("/store")
    @Operation(summary = "점포 삭제")
    public ResponseEntity<MessageResponse> deleteStore(@RequestHeader("Authorization") String token) {
        storeService.deleteStoreByToken(token);  // token을 통해 점포 삭제
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 삭제 성공"));
    }

    // 9. 점포 휴무일 변경
    @PatchMapping("/{id}/closedDays")
    @Operation(summary = "점포 휴무일 변경")
    public ResponseEntity<MessageResponse> updateClosedDays(@RequestHeader("Authorization") String token, @RequestBody String closedDays) {
        storeService.updateClosedDays(token, closedDays);  // token을 통해 userId로 점포를 찾고 휴무일 업데이트
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("점포 휴무일 변경 성공"));
    }

    @PatchMapping("/store/ownerWord")
    @Operation(summary = "점포 사장님 한마디 수정")
    public ResponseEntity<MessageResponse> updateOwnerWord(@RequestHeader("Authorization") String token, @RequestBody String ownerWord) {
        storeService.updateOwnerWord(token, ownerWord);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.of("사장님 한마디 수정 성공"));
    }
}