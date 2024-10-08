package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.TopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopCategoryRepository extends JpaRepository<TopCategory, Integer> {
}