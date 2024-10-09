package io.ssafy.p.j11a307.user.dto;

import java.util.List;

public record GlobalDibsAlertRequest(
        List<Integer> storeIds,
        String fcmToken
) {}
