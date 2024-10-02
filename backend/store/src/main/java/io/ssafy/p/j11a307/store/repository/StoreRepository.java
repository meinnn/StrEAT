package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
