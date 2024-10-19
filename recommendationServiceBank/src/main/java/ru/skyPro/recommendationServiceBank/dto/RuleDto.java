package ru.skyPro.recommendationServiceBank.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;
import ru.skyPro.recommendationServiceBank.repository.RuleRepository;


@Data
public class RuleDto {
    private String query;
    private String[] arguments;
    private boolean negate;


    public RuleDto(String query, String[] arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }
}
