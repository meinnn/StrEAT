package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Integer> {
}
