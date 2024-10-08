package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.OrdersId;
import io.ssafy.p.j11a307.order.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Transactional
    @Query(value = "SELECT * FROM review WHERE review_id= :id", nativeQuery = true)
    Review searchReview(Integer id);


    //List<Review> findByIdIn(List<OrdersId> reviewIds);
}
