package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOptionCategoryRepository extends JpaRepository<ProductOptionCategory, Integer> {
    List<ProductOptionCategory> findByProductId(Integer productId);
    Optional<ProductOptionCategory> findByProductIdAndName(Integer productId, String name);
}
