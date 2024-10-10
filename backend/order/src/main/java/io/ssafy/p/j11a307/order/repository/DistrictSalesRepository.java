package io.ssafy.p.j11a307.order.repository;

import io.ssafy.p.j11a307.order.entity.DistrictSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictSalesRepository extends JpaRepository<DistrictSales, Integer> {
    DistrictSales findByDistrictName(String districtName);
}
