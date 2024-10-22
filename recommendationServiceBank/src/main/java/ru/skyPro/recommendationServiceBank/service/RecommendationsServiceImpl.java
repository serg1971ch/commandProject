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
}

