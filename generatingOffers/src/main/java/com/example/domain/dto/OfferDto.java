package com.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfferDto {
    private Long userId;
    private byte[] offer;
}
