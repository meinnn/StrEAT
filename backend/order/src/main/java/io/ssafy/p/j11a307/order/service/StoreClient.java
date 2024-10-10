package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.ReadStoreBasicInfoDTO;
import io.ssafy.p.j11a307.order.dto.ReadStoreCategoryDTO;
import io.ssafy.p.j11a307.order.dto.ReadStoreDTO;
import io.ssafy.p.j11a307.order.dto.GetStoreSimpleLocationDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STORE")
public interface StoreClient {
    @GetMapping("/api/stores/user/{userId}")
    Integer getStoreIdByUserId(@PathVariable Integer userId);

    @GetMapping("/api/stores/{id}")
    DataResponse<ReadStoreDTO> getStoreInfo(@PathVariable Integer id);

    @GetMapping("/api/stores/{id}/photo-name")
    DataResponse<ReadStoreBasicInfoDTO> getStoreBasicInfo(@PathVariable Integer id);

    @GetMapping("/api/stores/locations/store/{storeId}/current-loc")
    Integer getSelectedSimpleLocationByStoreId (@PathVariable Integer storeId);

    @GetMapping("/api/stores/locations/{id}/info")
    GetStoreSimpleLocationDTO getStoreSimpleLocationInfo(@PathVariable Integer id);

    @GetMapping("/api/stores/category/{storeId}")
    ReadStoreCategoryDTO getStoreCategoryByStoreId(@PathVariable Integer storeId);

}