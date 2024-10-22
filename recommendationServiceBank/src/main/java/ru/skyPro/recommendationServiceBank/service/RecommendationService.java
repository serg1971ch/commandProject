package ru.skyPro.recommendationServiceBank.service;

import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;

import java.util.List;
import java.util.UUID;

public interface RecommendationService {
    ClientRecommendation getClientRecommendationByJDBCTemplate(UUID user);

    void setRecommendationRules();

    BankRecommendationRule createDynamicRule();

    List<BankRecommendationRule> getAllRules();

    void removeRule(int id);

    BankRecommendationRule getRuleByUserId(UUID id);

}
