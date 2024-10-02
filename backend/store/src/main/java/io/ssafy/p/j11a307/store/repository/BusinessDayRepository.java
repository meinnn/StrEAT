package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.BusinessDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessDayRepository extends JpaRepository<BusinessDay, Integer> {
    Optional<BusinessDay> findByStoreId(Integer storeId);
    boolean existsByStoreId(Integer storeId);
}