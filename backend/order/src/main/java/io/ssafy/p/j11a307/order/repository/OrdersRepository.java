package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.PayTypeCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Page<Orders> findByUserId(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.status = :status1 OR o.status = :status2)", nativeQuery = true)
    Page<Orders> findByStoreId(Integer storeId, String status1,String status2, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.user_id = :userId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id)", nativeQuery = true)
    Page<Orders> findByUserIdAndHasReview(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id)", nativeQuery = true)
    Page<Orders> findByStoreIdAndHasReview(Integer storeId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id)", nativeQuery = true)
    List<Orders> findByStoreIdAndHasReview(Integer storeId);

    Page<Orders> findByStoreIdAndStatusInAndPaymentMethodIn(Integer storeId, List<OrderCode> status, List<PayTypeCode> paymentMethod, Pageable pageable);
    Page<Orders> findByStoreIdAndStatusIn(Integer storeId,List<OrderCode> status, Pageable pageable);
    Page<Orders> findByStoreIdAndPaymentMethodIn(Integer storeId,List<PayTypeCode> paymentMethod, Pageable pageable);

    Page<Orders> findByStoreIdAndStatusInAndPaymentMethodInAndCreatedAtBetween(Integer storeId,List<OrderCode> status, List<PayTypeCode> paymentMethod, LocalDateTime createdAt, LocalDateTime createdAt2, Pageable pageable);
    Page<Orders> findByStoreIdAndStatusInAndCreatedAtBetween(Integer storeId,List<OrderCode> status, LocalDateTime createdAt, LocalDateTime createdAt2, Pageable pageable);

    Page<Orders> findByStoreIdAndPaymentMethodInAndCreatedAtBetween(Integer storeId,List<PayTypeCode> paymentMethod, LocalDateTime createdAt, LocalDateTime createdAt2, Pageable pageable);

    Page<Orders> findByStoreIdAndCreatedAtBetween(Integer storeId,LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<Orders> findAllByStoreId(Integer storeId, Pageable pageable);
}
