package io.ssafy.p.j11a307.push_alert.dto;

import java.util.List;

public record PushAlertHistoryResponse(
        List<PushAlertDetailResponse> pushAlertResponses,
        Long totalDataCount,
        int totalPageCount
) {}
