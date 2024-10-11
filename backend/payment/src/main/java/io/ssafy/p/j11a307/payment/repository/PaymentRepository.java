package io.ssafy.p.j11a307.payment.repository;

import io.ssafy.p.j11a307.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<Payment> findByOrderId(String orderId);
}
