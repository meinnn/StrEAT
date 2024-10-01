package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByUserId(Integer userId);

    List<Orders> findByStoreId(Integer storeId);
}
