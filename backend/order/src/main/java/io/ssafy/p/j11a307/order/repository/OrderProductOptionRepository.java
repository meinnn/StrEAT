package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.OrderProduct;
import io.ssafy.p.j11a307.order.entity.OrderProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductOptionRepository extends JpaRepository<OrderProductOption, Integer> {

    @Query(value="select product_option_id from order_product_option where order_product_id = :orderProductId", nativeQuery = true)
    List<Integer> findByOrderProductId(Integer orderProductId);
}
