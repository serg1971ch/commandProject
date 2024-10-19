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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rule> rules;

    public Recommendation(long id, String productName, String description ) {
        this.id = id;
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

//    public void setRule(List<Rule> rule) {
//        this.rule = rule;
//    }
//
//    public List<Rule> getRule() {
//        return rule;
//    }

    @Override
    public String toString() {
        return "Клиенту рекомендуется: " + productName + " -  " + description;
    }

}

