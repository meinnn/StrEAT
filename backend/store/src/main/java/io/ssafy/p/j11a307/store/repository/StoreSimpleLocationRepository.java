package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.StoreSimpleLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreSimpleLocationRepository extends JpaRepository<StoreSimpleLocation, Integer> {
    List<StoreSimpleLocation> findByStoreId(Integer storeId);
}
