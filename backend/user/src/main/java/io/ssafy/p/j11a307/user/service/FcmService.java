package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.dto.GlobalDibsAlertRequest;
import io.ssafy.p.j11a307.user.global.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "push-alert")
public interface FcmService {

    @PostMapping("/api/push-alert/dibs/{storeId}")
    void subscribeStore(@PathVariable Integer storeId,
                        @RequestHeader(value = "X-Internal-Request") String internalRequest,
                        @RequestParam Integer userId);

    @DeleteMapping("/api/push-alert/dibs/{storeId}")
    void unsubscribeStore(@PathVariable Integer storeId,
                          @RequestHeader(value = "X-Internal-Request") String internalRequest,
                          @RequestParam Integer userId);

    @PostMapping("/global/dibs-alert")
    MessageResponse turnOnAllDibsAlerts(@RequestBody GlobalDibsAlertRequest globalDibsAlertRequest,
                                        @RequestHeader(value = "X-Internal-Request") String internalRequest);

    @DeleteMapping("/global/dibs-alert")
    MessageResponse turnOffAllDibsAlerts(@RequestBody GlobalDibsAlertRequest globalDibsAlertRequest,
                                         @RequestHeader(value = "X-Internal-Request") String internalRequest);
}
