package com.example.service;

import com.example.domain.dto.OfferAnswerDto;
import com.example.domain.entity.offers.Offer;
import com.example.exception.ChangeOfferStatusException;
import com.example.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;


    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public List<Offer> getAllOffersByUsername(String username) {
        return offerRepository.getOffersByClient_Username(username);
    }

    public void setAnswer(OfferAnswerDto answer, String username) {
        Offer offer = offerRepository.getOfferById(answer.getOfferId());
        if (offer != null && offer.getClient().getUsername().equals(username)) {
            offer.setAnswer(answer.getAnswerStatus());
            offerRepository.save(offer);
            return;
        }
        throw new ChangeOfferStatusException();
    }
}
