package com.comrade.domain.ports.output.repository;

import com.comrade.domain.entity.Order;
import com.comrade.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    public Order save(Order order);
    public Optional<Order> findByTrackingId(TrackingId trackingId);
}
