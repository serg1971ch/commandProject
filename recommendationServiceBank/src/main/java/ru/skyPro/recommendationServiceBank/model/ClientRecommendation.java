package ru.skyPro.recommendationServiceBank.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ClientRecommendation {
    private UUID id;
    private List<BankRecommendation> bankRecommendations;

    public ClientRecommendation(UUID id, List<BankRecommendation> recommendations) {
        this.id = id;
        this.bankRecommendations = recommendations;
    }
}
