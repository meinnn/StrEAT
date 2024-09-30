package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryCategoryRepository extends JpaRepository<IndustryCategory, Integer> {
}