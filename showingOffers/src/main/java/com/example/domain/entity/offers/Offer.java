package com.example.domain.entity.offers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients_offers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Offer {
    @Id
    @Column(name = "clients_offers_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "pattern_id")
    private Long patternId;
    @Column(name = "answer")
    @Enumerated(EnumType.STRING)
    private AnswerStatus answer;
    @Column(name = "offer")
    private byte[] offer;
}
