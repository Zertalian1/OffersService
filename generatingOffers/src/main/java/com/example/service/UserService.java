package com.example.service;

import com.example.domain.dto.ShareUserDto;
import com.example.domain.entity.Client;
import com.example.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ClientRepository clientRepository;
    private final OfferService offerService;

    @KafkaListener(
            topics = "${application.kafka.client.topic}",
            containerFactory = "clientListenerContainerFactory"
    )
    public void test(@Payload ShareUserDto user) {
        Client client = clientRepository.getClientByUsername(user.getUsername());
        if (client == null) {
            client = new Client();
            client.setUsername(user.getUsername());
        }
        client.setFirstName(user.getFirstName());
        client.setLastName(user.getLastName());
        client.setPatronymic(user.getPatronymic());
        clientRepository.save(client);
        offerService.generateOfferForUser(client);
    }
}
