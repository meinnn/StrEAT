package io.ssafy.p.j11a307.product.repository;

import io.ssafy.p.j11a307.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
