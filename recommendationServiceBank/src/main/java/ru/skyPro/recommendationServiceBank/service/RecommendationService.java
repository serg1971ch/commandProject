package ru.skyPro.recommendationServiceBank.service;

import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;

import java.util.UUID;

public interface RecommendationService {
    ClientRecommendation getClientRecommendationByJDBCTemplate(UUID user);
/*
добавление правила,
удаление правила,
получение списка всех правил.
 */

    BankRecommendationRule createRule();
//    List<Rule> getAllRules();
//    void removeRule(Long id);

}
