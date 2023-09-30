package com.comrade.domain;

import com.comrade.domain.dto.track.TrackOrderQuery;
import com.comrade.domain.dto.track.TrackOrderResponse;
import com.comrade.domain.entity.Order;
import com.comrade.domain.exception.OrderNotFoundException;
import com.comrade.domain.mapper.OrderDataMapper;
import com.comrade.domain.ports.output.repository.OrderRepository;
import com.comrade.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper,
                                    OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery){
        Optional<Order> byTrackingId = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (byTrackingId.isEmpty()){
            log.warn("Could not find order with track id {} ",trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with track id  "+trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(byTrackingId.get());
    }
}
