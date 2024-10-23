package ru.skyPro.recommendationServiceBank.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.configuration.AppProperties;
import ru.skyPro.recommendationServiceBank.exceptions.RecommendBankException;
import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;
import ru.skyPro.recommendationServiceBank.repository.RecommendationRepository;
import ru.skyPro.recommendationServiceBank.repository.RecommendationsRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class RecommendationsServiceImpl implements RecommendationService {
    private final RecommendationRepository repository;
    private final CombinerQueryService combinerService;
    private final AppProperties appProperties;
    private final RecommendationsRepository recommendationsRepository;
    @Getter
    private List<BankRecommendationRule> recommendationsList;

    public RecommendationsServiceImpl(RecommendationRepository repository, CombinerQueryService combinerService, AppProperties appProperties,
                                      RecommendationsRepository recommendationsRepository, List<BankRecommendationRule> recommendationsList) {
        this.repository = repository;
        this.combinerService = combinerService;
        this.appProperties = appProperties;
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationsList = recommendationsList;
    }

    @Override
    public ClientRecommendation getClientRecommendationByJDBCTemplate(UUID user) {
        ClientRecommendation client;
        List<String> listProducts = recommendationsRepository.getRecommenationForUser(user);
        List<BankRecommendationRule> bankServiceRecommendations = new ArrayList<>();

        if (listProducts.contains(appProperties.getNameOne())) {
            bankServiceRecommendations.add(new BankRecommendationRule(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), appProperties.getNameOne(), appProperties.getDescriptionOne()));
            client = new ClientRecommendation(user, bankServiceRecommendations);
            return client;
        } else if (listProducts.contains(appProperties.getNameTwo())) {
            bankServiceRecommendations.add(new BankRecommendationRule(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), appProperties.getNameTwo(), appProperties.getDescriptionTwo()));
            client = new ClientRecommendation(user, bankServiceRecommendations);
            return client;
        } else if (listProducts.contains(appProperties.getNameThree())) {
            bankServiceRecommendations.add(new BankRecommendationRule(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), appProperties.getNameThree(), appProperties.getDescriptionThree()));
            client = new ClientRecommendation(user, bankServiceRecommendations);
            return client;
        } else {
            throw new RecommendBankException("recommendation not found");
        }
    }

    @Override
    public void setRecommendationRules() {
        BankRecommendationRule ruleOne;
        BankRecommendationRule ruleTwo;
        BankRecommendationRule ruleThree;
        recommendationsList = new ArrayList<>();
        ruleOne = combinerService.getRecommendation(1L);
        ruleTwo = combinerService.getRecommendation(2L);
        ruleThree = combinerService.getRecommendation(3L);
        recommendationsList.add(ruleOne);
        recommendationsList.add(ruleTwo);
        recommendationsList.add(ruleThree);
    }

    @Override
    public BankRecommendationRule createDynamicRule() {
        return recommendationsList.get(2);
    }

    @Override
    public List<BankRecommendationRule> getAllRules() {
        setRecommendationRules();
        return recommendationsList;
    }

    @Override
    public void removeRule(int id) {
        recommendationsList.remove(id);
    }

    @Override
    public BankRecommendationRule getRuleByUserId(UUID id) {
        String startQuery = "SELECT CASE ";
        String finishQuery = "END AS result FROM PRODUCTS p JOIN TRANSACTIONS t ON t.product_id = p.id " +
                             "WHERE t.user_id = ?";
//                    /                    "WHEN " +
//                    "SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 " +
//                    "AND SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 " +
//                    "AND SUM(CASE WHEN p.type = 'SAVING' AND t.amount > 0 THEN t.amount ELSE 0 END) > 1000 " +
//                    "THEN 'Invest500' " +
//                    "WHEN " +
//                    "SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 " +
//                    "AND (SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) >= 50000 " +
//                    "OR SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) >= 50000) " +
//                    "AND SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'WITHDRAW' THEN t.amount ELSE 0 END) " +
//                    "THEN 'Top Saving'" +
//                    "WHEN " +
//                    "SUM(CASE WHEN p.type = 'CREDIT' THEN 1 ELSE 0 END) = 0 " +
//                    "AND SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > SUM(CASE WHEN p.type = 'WITHDRAW' THEN t.amount ELSE 0 END) " +
//                    "AND SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) > 100000 " +
//                    "THEN 'Simple Credit' " +
//                    "ELSE '0' " +
        "END AS result " +
                "FROM PRODUCTS p " +
                "JOIN TRANSACTIONS t ON t.product_id = p.id " +
                "WHERE t.user_id = ?";
    }
}

