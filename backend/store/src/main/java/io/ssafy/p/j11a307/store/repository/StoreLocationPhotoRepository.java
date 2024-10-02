package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreLocationPhotoRepository extends JpaRepository<StoreLocationPhoto, Integer> {
    List<StoreLocationPhoto> findByStoreId(Integer storeId);
}
