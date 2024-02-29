package com.example.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    @ManyToOne
    @JoinColumn(name = "pattern_id")
    private OfferPattern pattern;
    @Column(name = "show_date")
    private LocalDate showDate;
    @Column(name = "answer")
    @Enumerated(EnumType.STRING)
    private AnswerStatus answer;
}
