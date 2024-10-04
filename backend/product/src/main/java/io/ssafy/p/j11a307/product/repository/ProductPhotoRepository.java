package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {

    // 특정 productId에 해당하는 모든 ProductPhoto 조회
    List<ProductPhoto> findByProductId(Integer productId);

    // storeId를 기준으로 ProductPhoto 조회
    List<ProductPhoto> findByProductStoreId(Integer storeId);

    // 특정 storeId와 productId에 해당하는 모든 ProductPhoto 조회
    List<ProductPhoto> findByProductStoreIdAndProductId(Integer storeId, Integer productId);

}
