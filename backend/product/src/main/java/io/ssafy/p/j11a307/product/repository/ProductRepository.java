package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // storeId로 상품 목록 조회
    List<Product> findByStoreId(Integer storeId);

    @Query("SELECT pc.name FROM ProductCategory pc JOIN pc.product p WHERE p.storeId = :storeId")
    List<String> findCategoriesByStoreId(@Param("storeId") Integer storeId);
}
