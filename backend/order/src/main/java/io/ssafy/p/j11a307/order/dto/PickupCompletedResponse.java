package io.ssafy.p.j11a307.order.dto;

import java.util.List;

public record PickupCompletedResponse(
        List<Integer> pickupCompletedOrderIds
) {}
