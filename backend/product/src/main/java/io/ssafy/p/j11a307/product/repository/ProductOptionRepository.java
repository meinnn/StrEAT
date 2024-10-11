package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    Optional<ProductOption> findById(Integer id);

    List<ReadProductOptionDTO> findAllByProduct(Product product);

    List<ProductOption> findByProductOptionCategoryId(Integer productOptionCategoryId);

    Optional<ProductOption> findByProductOptionCategoryIdAndProductOptionName(Integer productOptionCategoryId, String optionName);
}
