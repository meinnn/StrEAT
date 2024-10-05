package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.BusinessDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessDayRepository extends JpaRepository<BusinessDay, Integer> {
    List<BusinessDay> findByStoreId(Integer storeId);
}