package io.ssafy.p.j11a307.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ssafy.p.j11a307.store.dto.UpdateStoreDTO;
import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 20)
    private String businessRegistrationNumber;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 20)
    private Double latitude;

    @Column(nullable = false, length = 20)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StoreType type;

    @Column(nullable = false, length = 50)
    private String bankAccount;

    @Column(nullable = false, length = 20)
    private String bankName;

    private String ownerWord;

    private String storePhoneNumber;

    private String closedDays;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StoreStatus status;

    // 관계 설정
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore  // 순환 참조 방지
    private List<StorePhoto> storePhotos;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore  // 순환 참조 방지
    private List<StoreLocationPhoto> storeLocationPhotos;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreSimpleLocation> simpleLocations;

    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL)
    @JsonIgnore  // 순환 참조 방지
    private BusinessDay businessDay;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "selected_simple_location_id")
    private StoreSimpleLocation selectedSimpleLocation;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    @JsonIgnore
    private SubCategory subCategory;



    // 이름 변경 메서드
    public void changeName(String name) {
        if (name == null || name.isEmpty()) {
              throw new BusinessException(ErrorCode.STORE_NAME_NULL);
        }
        this.name = name;
    }

    public void changeOwnerWord(String ownerWord) {
        this.ownerWord = ownerWord;
    }

    // 주소 변경 메서드
    public void changeAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new BusinessException(ErrorCode.STORE_ADDRESS_NULL);
        }
        this.address = address;
    }

    public void changeLatitude(Double latitude) {
        if (latitude == null) {
            throw new BusinessException(ErrorCode.STORE_LATITUDE_NULL);
        }
        this.latitude = latitude;
    }

    // 경도 변경 메서드
    public void changeLongitude(Double longitude) {
        if (longitude == null) {
            throw new BusinessException(ErrorCode.STORE_LONGITUDE_NULL);
        }
        this.longitude = longitude;
    }

    public void changeClosedDays(String closedDays) {
        this.closedDays = closedDays;
    }

    public void changeStatus(StoreStatus status) {
        if (status == null) {
            throw new BusinessException(ErrorCode.STORE_STATUS_NULL);
        }
        this.status = status;
    }

    // 사용자 ID 설정 메서드
    public void assignOwner(Integer userId) {
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }
        this.userId = userId;
    }

    public void updateSelectedSimpleLocation(StoreSimpleLocation simpleLocation) {
        if (simpleLocation == null) {
            throw new BusinessException(ErrorCode.SIMPLE_LOCATION_NOT_FOUND);
        }

        this.selectedSimpleLocation = simpleLocation;
    }

    public Store updateWith(UpdateStoreDTO request, SubCategory subCategory) {
        return Store.builder()
                .id(this.id)  // ID는 변경하지 않음
                .userId(this.userId)  // Owner ID는 그대로 유지
                .businessRegistrationNumber(request.businessRegistrationNumber() != null ? request.businessRegistrationNumber() : this.businessRegistrationNumber)  // 사업자 등록번호 처리 추가
                .name(request.name() != null ? request.name() : this.name)
                .address(request.address() != null ? request.address() : this.address)
                .latitude(request.latitude() != null ? request.latitude() : this.latitude)
                .longitude(request.longitude() != null ? request.longitude() : this.longitude)
                .type(request.type() != null ? request.type() : this.type)
                .bankAccount(request.bankAccount() != null ? request.bankAccount() : this.bankAccount)
                .bankName(request.bankName() != null ? request.bankName() : this.bankName)
                .ownerWord(request.ownerWord() != null ? request.ownerWord() : this.ownerWord)
                .storePhoneNumber(request.storePhoneNumber() != null ? request.storePhoneNumber() : this.storePhoneNumber)  // storePhoneNumber 추가
                .closedDays(request.closedDays() != null ? request.closedDays() : this.closedDays)
                .status(request.status() != null ? request.status() : this.status)
                .subCategory(subCategory)
                .build();
    }



    public Integer getStoreId() {
        return this.id;
    }
}
