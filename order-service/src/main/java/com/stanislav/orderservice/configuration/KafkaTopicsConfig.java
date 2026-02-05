package com.stanislav.orderservice.configuration;

import com.stanislav.orderservice.kafka.KafkaTopicsProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic orderTopic(KafkaTopicsProperties topics) {
        return TopicBuilder.name(topics.orders())
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentsTopic(KafkaTopicsProperties topics) {
        return TopicBuilder.name(topics.orders())
                .partitions(3)
                .replicas(1)
                .build();
    }
}
