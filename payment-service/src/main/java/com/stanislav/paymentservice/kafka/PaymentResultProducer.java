package com.stanislav.paymentservice.kafka;

import com.stanislav.paymentservice.api.dto.PaymentResultEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultProducer {

    private final KafkaTemplate<String, PaymentResultEvent> kafkaTemplate;
    private final String paymentTopic;


    public PaymentResultProducer(KafkaTemplate<String, PaymentResultEvent> kafkaTemplate,
                                 @Value("${app.kafka.topics.payments}") String paymentTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentTopic = paymentTopic;
    }

    public void publish(PaymentResultEvent event){
        kafkaTemplate.send(paymentTopic, event.orderId().toString(), event);
    }
}
