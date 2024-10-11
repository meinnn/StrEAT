package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.Orders;
import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.PayTypeCode;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    Orders findByOrderNumber(String orderNumber);

    Page<Orders> findByUserId(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.status = :status1 OR o.status = :status2)", nativeQuery = true)
    Page<Orders> findByStoreId(Integer storeId, String status1,String status2, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.status = :status1 OR o.status = :status2)", nativeQuery = true)
    List<Orders> findByStoreId(Integer storeId, String status1,String status2);

    @Query(value = "SELECT * FROM orders o WHERE id < :ordersId AND (o.store_id = :storeId AND o.status = :status1 )", nativeQuery = true)
    List<Orders> findByStoreId(Integer storeId, String status1,Integer ordersId);

    List<Orders> findByStoreId(Integer storeId);

    @Query(value = "SELECT * FROM orders o WHERE o.user_id = :userId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id) ORDER BY (SELECT MAX(r.created_at) FROM review r WHERE r.review_id = o.id) DESC", nativeQuery = true)
    Page<Orders> findByUserIdAndHasReview(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id) ORDER BY (SELECT MAX(r.created_at) FROM review r WHERE r.review_id = o.id) DESC", nativeQuery = true)
    Page<Orders> findByStoreIdAndHasReview(Integer storeId, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND EXISTS (SELECT * FROM review r WHERE r.review_id = o.id)", nativeQuery = true)
    List<Orders> findByStoreIdAndHasReview(Integer storeId);

    @Query(value="SELECT * FROM orders WHERE user_id = :userId AND (status= 'PROCESSING' OR STATUS= 'WAITING_FOR_RECEIPT')", nativeQuery = true)
    List<Orders> findByUserIdAndOngoing(Integer userId);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND ((o.status IN :status) AND (o.payment_method IN :paymentMethod))", nativeQuery = true)
    Page<Orders> findByStoreIdAndStatusInOrPaymentMethodIn(Integer storeId, List<String> status, List<String> paymentMethod, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.status IN :status)", nativeQuery = true)
    Page<Orders> findByStoreIdAndStatusIn(Integer storeId,List<String> status, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.payment_method IN :paymentMethod)", nativeQuery = true)
    Page<Orders> findByStoreIdAndPaymentMethodIn(Integer storeId,List<String> paymentMethod, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE (o.store_id = :storeId) AND ((o.status IN :status) AND (o.payment_method IN :paymentMethod)) AND (o.created_at BETWEEN :startDate AND :endDate)", nativeQuery = true)
    Page<Orders> findByStoreIdAndStatusInOrPaymentMethodInAndCreatedAtBetween(Integer storeId,List<String> status, List<String> paymentMethod, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.status IN :status ) AND (o.created_at BETWEEN :startDate AND :endDate) ", nativeQuery = true)
    Page<Orders> findByStoreIdAndStatusInAndCreatedAtBetween(Integer storeId,List<String> status, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.payment_method IN :paymentMethod ) AND (o.created_at BETWEEN :startDate AND :endDate)", nativeQuery = true)
    Page<Orders> findByStoreIdAndPaymentMethodInAndCreatedAtBetween(Integer storeId,List<String> paymentMethod, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId AND (o.created_at BETWEEN :startDate AND :endDate) ", nativeQuery = true)
    Page<Orders> findByStoreIdAndCreatedAtBetween(Integer storeId,LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query(value = "SELECT * FROM orders o WHERE o.store_id = :storeId", nativeQuery = true)
    Page<Orders> findAllByStoreId(Integer storeId, Pageable pageable);

    @Query(value = "SELECT * FROM orders WHERE store_id= :storeId AND MONTH(created_at) = :thisMonth", nativeQuery = true)
    List<Orders> findByStoreIdAndThisMonth(@Param("thisMonth") Integer thisMonth, @Param("storeId") Integer storeId);


    @Query(value = "SELECT * FROM orders WHERE store_id = :storeId AND ((YEAR(paid_at)= YEAR(:date) AND MONTH(paid_at) = Month(:date)) AND DAY(paid_at)= DAY(:date))", nativeQuery = true)
    List<Orders> findOrdersByYearAndMonthAndDay(Integer storeId, LocalDateTime date);

    @Query(value = "SELECT * FROM orders WHERE store_id = :storeId AND (YEAR(paid_at)= YEAR(:date) AND MONTH(paid_at) = Month(:date))", nativeQuery = true)
    List<Orders> findOrdersByYearAndMonth(Integer storeId, LocalDateTime date);

    @Query(value = "SELECT * FROM orders WHERE store_id = :storeId AND (YEAR(paid_at)= YEAR(:date))", nativeQuery = true)
    List<Orders> findOrdersByYear(Integer storeId, LocalDateTime date);

    @Query(value = "SELECT * FROM orders WHERE store_id = :storeId AND (paid_at BETWEEN :weekStart AND :weekEnd)", nativeQuery = true)
    List<Orders> findOrdersByWeek(Integer storeId, LocalDateTime weekStart, LocalDateTime weekEnd);

    List<Orders> findAllByStoreIdAndUserIdAndStatus(Integer storeId, Integer userId, OrderCode status);
}
