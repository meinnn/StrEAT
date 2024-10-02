package io.ssafy.p.j11a307.payment.repository;

import io.ssafy.p.j11a307.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
