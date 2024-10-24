package ru.skyPro.recommendationServiceBank.model.rulesEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recommendations")
public class Recommendation {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String description;

    // В этой стороне определяем, что рекомендация может иметь много правил
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "recommendation_rule", // Имя таблицы-соединения
//            joinColumns = @JoinColumn(name = "recommendation_id"), // Имя столбца для Recommendation
//            inverseJoinColumns = @JoinColumn(name = "rule_id") // Имя столбца для Rule
//    )
    @OneToMany
    private List<Rule> rules;

    public Recommendation(String productName, String description) {
//        this.id = id;
        this.productName = productName;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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
    public String toString() {
        return "Клиенту рекомендуется: " + productName + " -  " + description;
    }
}

