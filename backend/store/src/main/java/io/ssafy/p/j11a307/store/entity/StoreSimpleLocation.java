package io.ssafy.p.j11a307.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreSimpleLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore
    private Store store;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 20)
    private Double latitude;

    @Column(nullable = false, length = 20)
    private Double longitude;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "storeSimpleLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreLocationPhoto> locationPhotos;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public void updateLocation(String address, Double latitude, Double longitude, String nickname, LocalDateTime localDateTime) {
        if (address != null && !address.isEmpty()) {
            this.address = address;
        }

        if (latitude != null && latitude > -90 && latitude < 90) {
            this.latitude = latitude;
        }

        if (longitude != null && longitude > -180 && longitude < 180) {
            this.longitude = longitude;
        }

        if(nickname != null && !nickname.isEmpty()) {
            this.nickname = nickname;
        }
        if (localDateTime != null) {
            this.createdAt = localDateTime;
        }
    }
}
