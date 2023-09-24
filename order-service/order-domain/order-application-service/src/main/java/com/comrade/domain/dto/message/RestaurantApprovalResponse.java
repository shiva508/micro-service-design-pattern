package com.comrade.domain.dto.message;

import com.comrade.domain.valueobject.OrderApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalResponse {
    private String id;
    private String sagaId;
    private String orderId;
    private String restaurantId;
    private Instant cratedAt;
    private OrderApprovalStatus orderApprovalStatus;
    private List<String> failureMessages;
}
