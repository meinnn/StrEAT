package io.ssafy.p.j11a307.store.repository;

import io.ssafy.p.j11a307.store.entity.BusinessDay;
import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLocationPhotoRepository extends JpaRepository<StoreLocationPhoto, Integer> {
}
