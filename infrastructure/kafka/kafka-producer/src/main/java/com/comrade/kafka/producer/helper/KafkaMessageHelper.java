package com.comrade.kafka.producer.helper;

import com.comrade.domain.exception.OrderDomainException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaMessageHelper {

    private final ObjectMapper objectMapper;

    public KafkaMessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new OrderDomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }
    public <T> BiConsumer<SendResult<String, T>,Throwable> getKafkaCallback(String responseTopicName,
                                                                           T avroModel,
                                                                           String orderId,
                                                                           String avroModelName) {

        return (stringTSendResult, throwable) -> {
            if (throwable == null) {
                RecordMetadata metadata = stringTSendResult.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {} Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        orderId,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
            }else {
                log.error("Error while sending " + avroModelName + " message {} to topic {}", avroModel.toString(), responseTopicName, throwable.getMessage());
            }
        };

    }
}
