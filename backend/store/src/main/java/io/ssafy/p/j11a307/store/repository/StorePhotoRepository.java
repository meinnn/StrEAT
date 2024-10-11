package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorePhotoRepository extends JpaRepository<StorePhoto, Integer> {
    List<StorePhoto> findByStoreId(Integer storeId);

}
