package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.OrderProduct;
import io.ssafy.p.j11a307.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
    //해당 주문에 따른 주문 상품들
    List<OrderProduct> findByOrdersId(Orders ordersId);
    void deleteByOrdersId(Orders ordersId);

}
