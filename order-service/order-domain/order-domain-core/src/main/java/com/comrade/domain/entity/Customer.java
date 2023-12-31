package com.comrade.domain.entity;

import com.comrade.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId>{
    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
