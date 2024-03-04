package com.example.controller;

import com.example.domain.dto.OfferAnswerDto;
import com.example.domain.dto.OfferDto;
import com.example.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDto>> getOffers(Principal principal) {
        List<OfferDto> offers = offerService.getAllOffersByUsername(principal.getName()).stream().map(ent ->
                new OfferDto(ent.getId(), new String(ent.getOffer()))).toList();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/offers-all")
    public ResponseEntity<List<OfferDto>> getAllOffers() {
        List<OfferDto> offers = offerService.getAllOffers().stream().map(ent ->
                new OfferDto(ent.getId(), new String(ent.getOffer()))).toList();
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/offers")
    public ResponseEntity<Void> setOfferStatus(
            @RequestBody OfferAnswerDto answerDto,
            Principal principal
    ) {
        offerService.setAnswer(answerDto, principal.getName());
        return ResponseEntity.ok().build();
    }
}
