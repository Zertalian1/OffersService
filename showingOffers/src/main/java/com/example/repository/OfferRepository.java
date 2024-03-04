package com.example.repository;


import com.example.domain.entity.offers.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> getOffersByClient_Username(String username);

    Offer getOfferById(Long offerId);

}
