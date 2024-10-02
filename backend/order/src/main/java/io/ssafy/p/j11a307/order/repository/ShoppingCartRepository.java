package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Review;
import io.ssafy.p.j11a307.order.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository  extends JpaRepository<ShoppingCart, Integer> {
    //장바구니 테이블에서 이 유저와 동일한 상품들 검색
    List<ShoppingCart> findAllByCustomerIdAndProductId(Integer customerId, Integer productId);
}
