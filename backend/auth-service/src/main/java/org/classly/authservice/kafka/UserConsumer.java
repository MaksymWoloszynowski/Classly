package org.classly.authservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.classly.authservice.dto.AccessCodeRequestDTO;
import org.classly.authservice.mapper.AccessCodeMapper;
import org.classly.authservice.service.AccessCodeService;
import org.classly.events.UserCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {

    private final AccessCodeService accessCodeService;

    public UserConsumer(AccessCodeService accessCodeService) {
        this.accessCodeService = accessCodeService;
    }

    @KafkaListener(topics = "users")
    public void consume(byte[] data) {
        try {
            UserCreatedEvent event = UserCreatedEvent.parseFrom(data);

            AccessCodeRequestDTO request =
                    AccessCodeMapper.toRequestFromEvent(event);

            accessCodeService.createAccessCode(request);

        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException("Failed to parse UserCreatedEvent from Kafka message", e);
        }
    }
}