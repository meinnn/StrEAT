package io.ssafy.p.j11a307.push_alert.repository;

import io.ssafy.p.j11a307.push_alert.entity.PushAlert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushAlertRepository extends JpaRepository<PushAlert, Long> {

    Page<PushAlert> findByUserIdOrderByIdDesc(Integer userId, Pageable pageable);

    Long countByUserId(Integer userId);
}
