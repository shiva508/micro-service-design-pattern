package com.comrade.dataaccess.customer.mapper;

import com.comrade.dataaccess.customer.entity.CustomerEntity;
import com.comrade.domain.entity.Customer;
import com.comrade.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId().getValue())
                //.username(customer.getUsername())
                //.firstName(customer.getFirstName())
                //.lastName(customer.getLastName())
                .build();
    }
}
