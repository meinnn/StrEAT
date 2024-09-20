package io.ssafy.p.j11a307.user.repository;

import io.ssafy.p.j11a307.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
