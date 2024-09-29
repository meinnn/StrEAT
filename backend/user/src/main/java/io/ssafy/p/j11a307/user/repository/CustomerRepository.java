package io.ssafy.p.j11a307.user.repository;

import io.ssafy.p.j11a307.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
