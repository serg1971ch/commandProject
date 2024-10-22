package ru.skyPro.recommendationServiceBank.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ClientRecommendation {
    private UUID id;
    private List<BankRecommendationRule> bankRecommendations;

    public ClientRecommendation(UUID id, List<BankRecommendationRule> recommendations) {
        this.id = id;
        this.bankRecommendations = recommendations;
    }
}
