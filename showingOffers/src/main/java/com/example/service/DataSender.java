package com.example.service;

import java.util.List;

public interface DataSender<T> {
    void sendMessage(T generatedOffer);

    void sendMessage(List<T> generatedOfferList);
}
