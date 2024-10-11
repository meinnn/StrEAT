package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.ssafy.p.j11a307.store.entity.StoreSimpleLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreLocationPhotoRepository extends JpaRepository<StoreLocationPhoto, Integer> {
    // storeId와 locationPhotoId를 기준으로 StoreLocationPhoto를 찾는 메서드
    Optional<StoreLocationPhoto> findByIdAndStoreId(Integer id, Integer storeId);

    // 특정 storeId에 속한 모든 StoreLocationPhoto를 조회하는 메서드
    List<StoreLocationPhoto> findByStoreId(Integer storeId);

    void deleteByStoreSimpleLocation(StoreSimpleLocation storeSimpleLocation);

    List<StoreLocationPhoto> findByStoreSimpleLocationId(Integer simpleLocationId);
}
