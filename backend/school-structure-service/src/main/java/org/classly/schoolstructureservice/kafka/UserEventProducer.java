package org.classly.schoolstructureservice.kafka;

import org.classly.events.UserCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserCreatedEvent event) {
        kafkaTemplate.send(
                "users",
                event.getId(),
                event.toByteArray()
        );
    }
}