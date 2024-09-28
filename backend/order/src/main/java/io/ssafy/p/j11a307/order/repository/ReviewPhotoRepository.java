package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.ReviewPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Integer> {
}
