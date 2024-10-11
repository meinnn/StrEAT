package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // storeId로 상품 목록 조회
    List<Product> findByStoreId(Integer storeId);

    @Query("SELECT DISTINCT p.category.id FROM Product p WHERE p.storeId = :storeId")
    List<Integer> findDistinctCategoryIdsByStoreId(@Param("storeId") Integer storeId);

    @Query("SELECT pc.name FROM ProductCategory pc WHERE pc.id IN :categoryIds")
    List<String> findCategoryNamesByIds(@Param("categoryIds") List<Integer> categoryIds);

    // storeId와 productId로 Product 조회
    Optional<Product> findByStoreIdAndId(Integer storeId, Integer productId);

    @Query("SELECT p.id FROM Product p ORDER BY p.id DESC LIMIT 1")
    Integer findLastProductId();
}
