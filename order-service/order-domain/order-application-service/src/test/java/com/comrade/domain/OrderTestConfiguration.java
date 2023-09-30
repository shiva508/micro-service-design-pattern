package com.comrade.domain;

import com.comrade.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.comrade.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.comrade.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.comrade.domain.ports.output.repository.CustomerRepository;
import com.comrade.domain.ports.output.repository.OrderRepository;
import com.comrade.domain.ports.output.repository.RestaurantRepository;
import com.comrade.domain.service.OrderDomainService;
import com.comrade.domain.service.OrderDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.comrade.domain")
public class OrderTestConfiguration {
    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher(){
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher(){
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher(){
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }
    @Bean
    public OrderRepository orderRepository(){
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository(){
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository(){
        return Mockito.mock(RestaurantRepository.class);
    }
    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
