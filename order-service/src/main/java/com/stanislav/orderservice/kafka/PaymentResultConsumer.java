package com.stanislav.orderservice.kafka;

import com.stanislav.orderservice.api.dto.PaymentResultEvent;
import com.stanislav.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class PaymentResultConsumer {

    private final OrderService orderService;

    public PaymentResultConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${app.kafka.topics.payments}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void on(PaymentResultEvent event) {
        log.info("Payment result received: orderId={}, status={}, paymentId={}",
                event.orderId(), event.status(), event.paymentId());

        if (event.status() == PaymentResultEvent.PaymentStatus.SUCCEEDED) {
            orderService.markOrderAsPaid(event.orderId());
        }else  {
            orderService.markFailed(event.orderId(), event.reason());
        }
    }
}
