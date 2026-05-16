package com.master.user.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public void sendUserCreatedEvent(Long userId) {
        String message = "USER_CREATED:" + userId;
        kafkaTemplate.send(TOPIC, message);
        log.info("Sent event: {}", message);
    }
}
