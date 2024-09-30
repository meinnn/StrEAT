package io.ssafy.p.j11a307.store.entity;

import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreLocationPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false, length = 20)
    private String latitude;

    @Column(nullable = false, length = 20)
    private String longitude;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 위도 값 변경 메서드
    public void updateLatitude(String newLatitude) {
        if (newLatitude == null || newLatitude.isEmpty()) {
            throw new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_LATITUDE_NULL);
        }
        this.latitude = newLatitude;
    }

    // 경도 값 변경 메서드
    public void updateLongitude(String newLongitude) {
        if (newLongitude == null || newLongitude.isEmpty()) {
            throw new BusinessException(ErrorCode.STORE_LOCATION_PHOTO_LONGITUDE_NULL);
        }
        this.longitude = newLongitude;
    }
}
