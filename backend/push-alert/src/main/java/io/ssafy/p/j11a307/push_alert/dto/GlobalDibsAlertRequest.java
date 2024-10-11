package io.ssafy.p.j11a307.push_alert.dto;

import java.util.List;

public record GlobalDibsAlertRequest(
        List<Integer> storeIds,
        String fcmToken
) {}
