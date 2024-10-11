package io.ssafy.p.j11a307.store.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "push-alert")
public interface PushAlertService {

    @GetMapping("/api/push-alert/open-store")
    void sendOpenStoreAlert(@RequestParam Integer storeId, @RequestParam String storeName,
                            @RequestHeader(value = "X-Internal-Request") String internalRequest);
}
