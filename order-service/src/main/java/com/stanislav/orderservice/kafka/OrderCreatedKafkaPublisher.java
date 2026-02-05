package com.stanislav.orderservice.kafka;

import com.stanislav.orderservice.api.dto.OrderCreateEvent;
import com.stanislav.orderservice.entity.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
public class OrderCreatedKafkaPublisher {

    private final KafkaTemplate<String, OrderCreateEvent> orderKafkaTemplate;
    private final KafkaTopicsProperties topics;

    public OrderCreatedKafkaPublisher(KafkaTemplate<String, OrderCreateEvent> orderKafkaTemplate, KafkaTopicsProperties topics) {
        this.orderKafkaTemplate = orderKafkaTemplate;
        this.topics = topics;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(OrderCreateEvent event) {
        orderKafkaTemplate.send(topics.orders(), event.orderId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        var meta = result.getRecordMetadata();
                        log.info("Kafka sent: topic={}, partition={}, offset={}, timestamp={}, key={}, orderId={}",
                                meta.topic(),
                                meta.partition(),
                                meta.offset(),
                                meta.timestamp(),
                                event.orderId().toString(),
                                event.orderId());
                    } else {
                        log.error("Kafka send failed: topic={}, key={}, orderId={}, error={}",
                                topics.orders(),
                                event.orderId().toString(),
                                event.orderId(),
                                ex.toString(),
                                ex);
                    }
                });
    }
}
