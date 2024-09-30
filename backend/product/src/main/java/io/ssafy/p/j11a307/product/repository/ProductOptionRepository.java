package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    Optional<ProductOption> findById(Integer id);
}
