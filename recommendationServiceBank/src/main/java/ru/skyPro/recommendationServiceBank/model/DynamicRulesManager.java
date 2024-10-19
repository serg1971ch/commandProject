package ru.skyPro.recommendationServiceBank.model;

import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;

import java.util.List;

public interface DynamicRulesManager {
    List<Rule> createListRules(Rule firstRule, Rule secondRule, Rule thirdRule);
}
