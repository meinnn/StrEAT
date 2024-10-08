package io.ssafy.p.j11a307.push_alert.repository;

import io.ssafy.p.j11a307.push_alert.entity.PushAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushAlertRepository extends JpaRepository<PushAlert, Long> {
}
