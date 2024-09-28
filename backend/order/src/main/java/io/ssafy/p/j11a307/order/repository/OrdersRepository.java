package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

}
