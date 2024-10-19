package ru.skyPro.recommendationServiceBank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
public class BankRecommendation implements DynamicRulesManager{

    private UUID id;
    private String productName;
    private String description;
    private List<Rule> rules;

    public BankRecommendation(UUID id, String productName, String description) {
        this.id = id;
        this.productName = productName;
        this.description = description;
    }

    public BankRecommendation(UUID id, String productName, String description, List<Rule> rulesList) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.rules = rulesList;
    }

    @Override
    public String toString() {
        return "Клиенту рекомендуется: " + productName + " -  " + description;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public List<Rule> createListRules(Rule firstRule, Rule secondRule, Rule thirdRule) {
        return List.of();
    }
}
