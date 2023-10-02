package com.comrade.messaging.producer;

import com.comrade.domain.config.OrderServiceConfigData;
import com.comrade.domain.event.OrderCancelledEvent;
import com.comrade.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.comrade.kafka.order.avro.model.PaymentRequestAvroModel;
import com.comrade.kafka.producer.helper.KafkaMessageHelper;
import com.comrade.kafka.producer.service.KafkaProducer;
import com.comrade.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {

        private final OrderMessagingDataMapper orderMessagingDataMapper;
        private final OrderServiceConfigData orderServiceConfigData;
        private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
        private final KafkaMessageHelper orderKafkaMessageHelper;

        public CancelOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                                OrderServiceConfigData orderServiceConfigData,
                                                KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer,
                                                KafkaMessageHelper orderKafkaMessageHelper) {
            this.orderMessagingDataMapper = orderMessagingDataMapper;
            this.orderServiceConfigData = orderServiceConfigData;
            this.kafkaProducer = kafkaProducer;
            this.orderKafkaMessageHelper = orderKafkaMessageHelper;
        }

        @Override
        public void publish(OrderCancelledEvent domainEvent) {
            String orderId = domainEvent.getOrder().getId().getValue().toString();
            log.info("Received OrderCancelledEvent for order id: {}", orderId);

            try {
                PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper
                        .orderCancelledEventToPaymentRequestAvroModel(domainEvent);

                kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                                   orderId,
                                   paymentRequestAvroModel,
                                   orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(),
                                                                            paymentRequestAvroModel,
                                                                            orderId,
                                                              "PaymentRequestAvroModel"
                                                                            )
                                   );

                log.info("PaymentRequestAvroModel sent to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());
            } catch (Exception e) {
                log.error("Error while sending PaymentRequestAvroModel message" +
                        " to kafka with order id: {}, error: {}", orderId, e.getMessage());
            }
        }
}
