package ru.skyPro.recommendationServiceBank.service;

import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Recommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;

import java.util.List;
import java.util.UUID;
@Service
public interface RecommendationService {
    ClientRecommendation getClientRecommendationByJDBCTemplate(UUID user);

    void setRecommendationRules();

    BankRecommendationRule createDynamicRule(Rule rule, Recommendation recommendation);

    List<BankRecommendationRule> getAllRules();

    void removeRule(int id);

    BankRecommendationRule getRuleByUserId(UUID id);

}
