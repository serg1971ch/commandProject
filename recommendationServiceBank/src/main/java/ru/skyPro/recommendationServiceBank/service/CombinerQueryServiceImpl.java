package ru.skyPro.recommendationServiceBank.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skyPro.recommendationServiceBank.dto.RuleDto;
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
    private final String DEBIT = "DEBIT";
    private final String CREDIT = "CREDIT";
    private final String INVEST = "INVEST";
    private final String SAVING = "SAVING";
    private final String USER = "USER_OF";
    private final String ACTIVE = "ACTIVE_USER_OF";
    private final String TRANSACTION_SUM = "TRANSACTION_SUM_COMPARE";
    private final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    private final RuleRepository ruleRepository;
    private final RecommendationRepository recommendationRepository;
    private static final Logger log = LoggerFactory.getLogger(CombinerQueryServiceImpl.class);
    private final ReturnTypeParser genericReturnTypeParser;

    @Autowired
    public CombinerQueryServiceImpl(RuleRepository ruleRepository, RecommendationRepository recommendationRepository, ReturnTypeParser genericReturnTypeParser) {
        this.ruleRepository = ruleRepository;
        this.recommendationRepository = recommendationRepository;
        this.genericReturnTypeParser = genericReturnTypeParser;
    }

    @Transactional
    public void saveQuery(Rule rule) {
        switch (rule.getQueryName()) {
            case USER ->
                    ruleRepository.save(new Rule(rule.getId(), rule.getQueryName(), rule.getArguments(), setQueryUserOfProduct()));
            case ACTIVE ->
                    ruleRepository.save(new Rule(rule.getId(), rule.getQueryName(), rule.getArguments(), setQueryActiveUserOfProduct()));
            case TRANSACTION_SUM -> ruleRepository.
                    save(new Rule(rule.getId(), rule.getQueryName(), rule.getArguments(), setQueryTransactionSummaryCompare()));
            case TRANSACTION_SUM_COMPARE -> ruleRepository.
                    save(new Rule(rule.getId(), rule.getQueryName(), rule.getArguments(), setQueryTransactionSumCompareDepositWithdraw()));
            default -> throw new IllegalStateException("Unexpected value: " + rule.getQueryName());
        }
    }

    @Override
    public String setQueryUserOfProduct() {
        Rule rule = ruleRepository.findByQueryName(USER);
        return "p.type  " + (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0];
    }

    @Override
    public String setQueryActiveUserOfProduct() {
        Rule rule = ruleRepository.findByQueryName(ACTIVE);
        return "SUM(CASE WHEN p.type " + (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0] + "THEN 1 ELSE 0 END) > 5 ";
    }

    @Override
    public String setQueryTransactionSummaryCompare() {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM);
        return "SUM(CASE WHEN p.type " +
                (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0] + " THEN t.amount" + (rule.isNegate() ? "!=" : "=") + rule.getArguments()[1] + " ELSE 0 END) " + rule.getArguments()[2] + rule.getArguments()[3];
    }

    @Override
    public String setQueryTransactionSumCompareDepositWithdraw() {
        Rule rule = ruleRepository.findByQueryName(TRANSACTION_SUM_COMPARE);
        return "SUM(CASE WHEN p.type " +
                (rule.isNegate() ? "!=" : "=") + rule.getArguments()[0] +
                " THEN t.amount = 'DEPOSIT'  ELSE 0 END) " + rule.getArguments()[1] +
                " SUM(CASE WHEN p.type = WITHDRAW  THEN t.amount = 'WITHDRAW' ELSE 0 END) ";
    }

    public BankRecommendation createDynamicRule(String firstNameRule, String secondNameRule, String thirdNameRule) {
        List<Rule> rulesList = new ArrayList<>();
        Rule firstRule = ruleRepository.findByQueryName(firstNameRule);
        saveQuery(firstRule);
        Rule secondRule = ruleRepository.findByQueryName(secondNameRule);
        saveQuery(secondRule);
        Rule thirdRule = ruleRepository.findByQueryName(thirdNameRule);
        saveQuery(thirdRule);
        rulesList.add(firstRule);
        rulesList.add(secondRule);
        rulesList.add(thirdRule);
        log.info("create dynamic rule is: {}", rulesList);
        Recommendation currentRecommend = recommendationRepository.findByProductName("Simple Credit");
        ruleRepository.updateQuery(currentRecommend, firstRule.getId());
        ruleRepository.updateQuery(currentRecommend, secondRule.getId());
        ruleRepository.updateQuery(currentRecommend, thirdRule.getId());
        log.info("recommendation: {}", currentRecommend);
        log.info("recommendation list rule: {}", rulesList);
        String[] chars = new String[]{firstNameRule, secondNameRule, thirdNameRule};
        Function<Rule, RuleDto> MAP_TO_DTO_FUNCTION = c -> new RuleDto(c.getQuery(), c.getArguments(), c.isNegate());
        return new BankRecommendation(UUID.randomUUID(), currentRecommend.getProductName(), currentRecommend.getDescription(), rulesList);
    }
}
