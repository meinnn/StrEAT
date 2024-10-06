package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductOptionCategoryRepository extends JpaRepository<ProductOptionCategory, Integer> {
    @Query("SELECT MAX(p.id) FROM ProductOptionCategory p")
    Integer findLastProductOptionCategoryId();
}
