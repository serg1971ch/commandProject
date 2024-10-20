package ru.skyPro.recommendationServiceBank.service;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.dto.RuleDto;
import ru.skyPro.recommendationServiceBank.exceptions.RecommendBankException;
import ru.skyPro.recommendationServiceBank.model.BankRecommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Recommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;
import ru.skyPro.recommendationServiceBank.repository.RecommendationRepository;
import ru.skyPro.recommendationServiceBank.repository.RuleRepository;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Service
public class CombinerQueryServiceImpl implements CombinerQueryService {
    private String DEBIT = "DEBIT";
    private final String CREDIT = "CREDIT";
    private final String INVEST = "INVEST";
    private final String SAVING = "SAVING";
    private final String USER = "USER_OF";
    private final String ACTIVE = "ACTIVE_USER_OF";
    private final String TRANSACTION_SUM = "TRANSACTION_SUM_COMPARE";
    private final String USER_FOR_INVEST = "USER_DONT_USE_INVEST";
    private final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    private final RuleRepository ruleRepository;
    private final RecommendationRepository recommendationRepository;
    private static final Logger log = LoggerFactory.getLogger(CombinerQueryServiceImpl.class);


    @Autowired
    public CombinerQueryServiceImpl(RuleRepository ruleRepository, RecommendationRepository recommendationRepository) {
        this.ruleRepository = ruleRepository;
        this.recommendationRepository = recommendationRepository;
    }

    public Rule setQueryUserOfProduct() {
        Rule rule = ruleRepository.findByQueryName(USER);
        DEBIT = new String(rule.getArguments(), StandardCharsets.UTF_8);
        rule.setQuery("p.type  " + (rule.isNegate() ? "!=" : "=") + DEBIT.replaceAll("[{}\"]", "").trim());
        return ruleRepository.save(rule);
    }

    public Rule setQueryActiveUserOfProduct() {
        Rule rule = ruleRepository.findByQueryName(ACTIVE);
        DEBIT = new String(rule.getArguments(), StandardCharsets.UTF_8);
//        Function<Rule, RuleDto> MAP_TO_DTO_FUNCTION = c -> new RuleDto(c.getQueryName(), arguments, c.isNegate());
        rule.setQuery("SUM(CASE WHEN p.type " + (rule.isNegate() ? "!=" : "=") + DEBIT.replaceAll("[{}\"]", "").trim() + "THEN 1 ELSE 0 END) > 5 ");
        return ruleRepository.save(rule);
    }

    public Rule setQueryTransactionSummaryCompare() {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM);
        String compareArgs = new String(rule.getArguments(), StandardCharsets.UTF_8);
        String[] splitArgs = compareArgs.split(",");
        rule.setQuery("SUM(CASE WHEN p.type " +
                (rule.isNegate() ? "!=" : "=") + splitArgs[0].replaceAll("[{}\"]", "").trim() + " THEN t.amount" +
                (rule.isNegate() ? "!=" : "=") + splitArgs[1].replaceAll("[{}\"]", "").trim() + " ELSE 0 END) " + splitArgs[2].replaceAll("[{}\"]", "").trim() + splitArgs[3].replaceAll("[{}\"]", "").trim());
        return ruleRepository.save(rule);
    }

    public Rule setQueryForUserInvest() {
        Rule rule = ruleRepository.findByQueryName(USER_FOR_INVEST);
        String compareTransactionArgs = new String(rule.getArguments(), StandardCharsets.UTF_8);
        String[] transactionArgs = compareTransactionArgs.split(",");
        rule.setQuery("p.type  " + (rule.isNegate() ? "!=" : "=") + transactionArgs[0].replaceAll("[{}\"]", "").trim());
        return ruleRepository.save(rule);
    }

    public Rule setQueryTransactionSumCompareDepositWithdraw() {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM_COMPARE);
        String compareTransactionSumArgs = new String(rule.getArguments(), StandardCharsets.UTF_8);
        String[] transactionSumArgs = compareTransactionSumArgs.split(",");
        rule.setQuery("SUM(CASE WHEN p.type " +
                (rule.isNegate() ? "!=" : "=") + transactionSumArgs[0].trim() +
                " THEN t.amount = 'DEPOSIT'  ELSE 0 END) " + transactionSumArgs[1].replaceAll("[{}\"]", "").trim() +
                " SUM(CASE WHEN p.type = WITHDRAW  THEN t.amount = 'WITHDRAW' ELSE 0 END) ");
        return ruleRepository.save(rule);
    }

    public List<Rule> createCreditRule() {
        return getRules(3L);
    }

    public List<Rule> createInvest500Rule() {
        return getRules(1L);
    }

    private List<Rule> getRules(Long id) {
        List<Rule> dynamicRuleList = new ArrayList<>();
        Rule firstRule = setQueryUserOfProduct();
        Rule secondRule = setQueryActiveUserOfProduct();
        Rule thirdRule = setQueryTransactionSummaryCompare();
        Rule thourthRule = setQueryForUserInvest();
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new RecommendBankException("recommendation not found"));
        if (recommendation.getId() == 1L) {
            dynamicRuleList.add(firstRule);
            dynamicRuleList.add(secondRule);
            dynamicRuleList.add(thourthRule);
            dynamicRuleList.forEach(rule -> {
                rule.setRecommendation(recommendation);
                rule.setRecommendation(recommendation);
                ruleRepository.save(rule);
            });
            return dynamicRuleList;
        }
//        if (recommendation.getId() == 2L) {
//            dynamicRuleList.add(firstRule);
//            dynamicRuleList.add(secondRule);
//            dynamicRuleList.add(thirdRule);
//            rule.setRecommendation(recommendation);
//            ruleRepository.save(rule);
//        }
        if (recommendation.getId() == 3L) {
            dynamicRuleList.add(firstRule);
            dynamicRuleList.add(secondRule);
            dynamicRuleList.add(thirdRule);
            dynamicRuleList.forEach(rule -> {
                rule.setRecommendation(recommendation);
                ruleRepository.save(rule);
            });
            return dynamicRuleList;
        }
        log.info("create Credit rule is: {}", dynamicRuleList.toString());
        return dynamicRuleList;
    }

    // Метод обновления Recommendation и связанных правил
    public BankRecommendation getRecommendation(Long id) {
        Recommendation recommendation = recommendationRepository.findById(id).orElseThrow(() -> new RecommendBankException("recommendation not found"));
        List<Rule> currentList = getRules(id);// здесь нужно получить лист в его нормальном виде для постмана
        log.info("this is recommendation has list of Rules: {}", recommendation.getRules().toString());
        return new BankRecommendation(UUID.randomUUID(),
                recommendation.getProductName(), recommendation.getDescription(), currentList
        );
    }
}

