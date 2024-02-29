package com.example.repository;

import com.example.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM clients_offers  where " +
                    "age(current_date, clients_offers.show_date) > INTERVAL '6 month' AND " +
                    "(clients_offers.answer = 'REJECTION' OR clients_offers.answer = 'ACCEPTED')",
            nativeQuery = true
    )
    void deleteOffersByShowDateMoreSixMonth();
}
