package com.comrade.domain.ports.input.message.listener.restaurantapproval;

import com.comrade.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    public void  orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);

}
