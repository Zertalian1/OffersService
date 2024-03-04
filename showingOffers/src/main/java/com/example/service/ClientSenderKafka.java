package com.example.service;

import com.example.domain.dto.ShareUserDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientSenderKafka implements DataSender<ShareUserDto> {

    private final NewTopic clientTopic;

    private final KafkaTemplate<String, ShareUserDto> kafkaTemplate;

    public void sendMessage(ShareUserDto generatedOffer) {
        kafkaTemplate.send(clientTopic.name(), generatedOffer);
    }

    @Override
    public void sendMessage(List<ShareUserDto> generatedOfferList) {
        for (ShareUserDto offer: generatedOfferList) {
            kafkaTemplate.send(clientTopic.name(), offer);
        }
    }
}
