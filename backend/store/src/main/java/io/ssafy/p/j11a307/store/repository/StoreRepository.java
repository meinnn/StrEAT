package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findByUserId(Integer userId);

    boolean existsByUserId(Integer userId);

//    @Query("SELECT s FROM Store s WHERE " +
//            "(s.latitude BETWEEN :latitude - 0.1 AND :latitude + 0.1) AND " +
//            "(s.longitude BETWEEN :longitude - 0.1 AND :longitude + 0.1)")
//    List<Store> findAllByLocationRange(@Param("latitude") double latitude, @Param("longitude") double longitude, Pageable pageable);


    @Query("SELECT s FROM Store s WHERE " +
            "(s.latitude BETWEEN :latitude - 0.1 AND :latitude + 0.1) AND " +
            "(s.longitude BETWEEN :longitude - 0.1 AND :longitude + 0.1)")
    Page<Store> findAllByLocationRange(@Param("latitude") double latitude, @Param("longitude") double longitude, Pageable pageable);
}

