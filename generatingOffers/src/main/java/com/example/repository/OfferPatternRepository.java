package com.example.repository;

import com.example.domain.entity.OfferPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferPatternRepository  extends JpaRepository<OfferPattern, Long> {
    @Query(
            value = "select patterns.pattern_id, patterns.directory, patterns.name from patterns " +
                    "left join clients_offers on patterns.pattern_id=clients_offers.clients_offers_id " +
                    "where clients_offers.answer != 'ANNOUNCE_LATER' AND " +
                    "((clients_offers.answer = 'REJECTION' AND age(current_date, clients_offers.show_date) > INTERVAL '1 month') OR " +
                    "(clients_offers.answer = 'ACCEPTED' AND age(current_date, clients_offers.show_date) > INTERVAL '3 month')) AND " +
                    "clients_offers.client_id = :clientId OR " +
                    "clients_offers.show_date IS NULL",
            nativeQuery = true
    )
    List<OfferPattern> selectPatternsByConditionsAndByUserId(Long clientId);
}
