package io.ssafy.p.j11a307.product.repository;


import io.ssafy.p.j11a307.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}