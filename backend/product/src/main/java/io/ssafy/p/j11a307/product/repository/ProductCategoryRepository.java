package io.ssafy.p.j11a307.product.repository;


import io.ssafy.p.j11a307.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    // 특정 productId에 속한 모든 카테고리 조회
    List<ProductCategory> findByProductId(Integer productId);
}