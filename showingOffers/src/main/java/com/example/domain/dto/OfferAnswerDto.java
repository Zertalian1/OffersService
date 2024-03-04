package com.example.domain.dto;

import com.example.domain.entity.offers.AnswerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfferAnswerDto {
    private Long offerId;
    private AnswerStatus answerStatus;
}
