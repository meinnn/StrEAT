package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Review;
import io.ssafy.p.j11a307.order.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findAllByCustomerIdAndProductId(Integer customerId, Integer productId);
    Page<ShoppingCart> findAllByCustomerId(Integer customerId, Pageable pageable);

    void deleteByCustomerId(Integer customerId);
}
