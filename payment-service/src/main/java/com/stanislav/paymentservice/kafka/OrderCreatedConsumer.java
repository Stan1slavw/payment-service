package com.stanislav.paymentservice.kafka;

import com.stanislav.paymentservice.api.dto.OrderCreateEvent;
import com.stanislav.paymentservice.api.dto.PaymentResultEvent;
import com.stanislav.paymentservice.entity.Payment;
import com.stanislav.paymentservice.entity.ProcessedEvent;
import com.stanislav.paymentservice.entity.enums.PaymentStatus;
import com.stanislav.paymentservice.repositories.PaymentRepository;
import com.stanislav.paymentservice.repositories.ProcessedEventRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Log4j2
@Component
public class OrderCreatedConsumer {

    private final ProcessedEventRepository processedEventRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentResultProducer paymentResultProducer;

    public OrderCreatedConsumer(ProcessedEventRepository processedEventRepository, PaymentRepository paymentRepository, PaymentResultProducer paymentResultProducer) {
        this.processedEventRepository = processedEventRepository;
        this.paymentRepository = paymentRepository;
        this.paymentResultProducer = paymentResultProducer;
    }

    @KafkaListener(topics = "${app.kafka.topics.orders}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void on(OrderCreateEvent event){
        try {
            processedEventRepository.save(new ProcessedEvent(event.eventId(), OffsetDateTime.now()));
        } catch (DataIntegrityViolationException e) {
            log.info("Duplicate key found in processed events: " + event.eventId());
            return;
        }
        //for test
        boolean success = event.amount().intValue() % 2 ==0;

        UUID paymentId = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        PaymentStatus status = success ? PaymentStatus.SUCCEEDED : PaymentStatus.FAILED;
        String providerRef = success ? "MOCK-" + paymentId : null;
        String reason = success ? null : "MOCK_DECLINED";

        paymentRepository.save(new Payment(paymentId, event.orderId(), status, providerRef, now));

        paymentResultProducer.publish(new PaymentResultEvent(UUID.randomUUID(), event.orderId(), paymentId, status, reason, now));

        log.info("Payment processed orderId={}, status={}", event.orderId(), status);
    }
}
