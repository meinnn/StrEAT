package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByUserId(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.status = :status1 OR o.status = :status2)", nativeQuery = true)
    Page<Orders> findByStoreId(Integer storeId, String status1,String status2, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.user_id = :userId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id)", nativeQuery = true)
    Page<Orders> findByUserIdAndHasReview(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id)", nativeQuery = true)
    Page<Orders> findByStoreIdAndHasReview(Integer storeId, Pageable pageable);

}
