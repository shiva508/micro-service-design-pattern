package com.comrade.domain.service;

import com.comrade.domain.entity.Order;
import com.comrade.domain.entity.Restaurant;
import com.comrade.domain.event.OrderCancelledEvent;
import com.comrade.domain.event.OrderCreatedEvent;
import com.comrade.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
   public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
   public OrderPaidEvent payOrder(Order order);
   public void approveOrder(Order order);
   public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);
}
