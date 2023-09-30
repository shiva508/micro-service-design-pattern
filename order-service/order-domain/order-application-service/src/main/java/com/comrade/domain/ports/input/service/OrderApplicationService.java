package com.comrade.domain.ports.input.service;

import com.comrade.domain.dto.create.CreateOrderCommand;
import com.comrade.domain.dto.create.CreateOrderResponse;
import com.comrade.domain.dto.track.TrackOrderQuery;
import com.comrade.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {

    public CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    public TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
