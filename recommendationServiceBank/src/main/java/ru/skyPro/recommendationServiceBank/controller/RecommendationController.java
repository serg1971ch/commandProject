package ru.skyPro.recommendationServiceBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Recommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;
import ru.skyPro.recommendationServiceBank.repository.RecommendationRepository;
import ru.skyPro.recommendationServiceBank.service.CombinerQueryService;
import ru.skyPro.recommendationServiceBank.service.RecommendationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService service;
    private final CombinerQueryService queryService;

    @Autowired
    public RecommendationController(RecommendationService service, CombinerQueryService queryService) {
        this.service = service;
        this.queryService = queryService;
    }

    @Operation(summary = "Add recommendation")
    @PostMapping("/rule")
    public ResponseEntity<BankRecommendationRule> addRecommendation(@RequestBody Rule rule,
                                                                    @RequestBody Recommendation recommendation) {
        return ResponseEntity.ok(queryService.getRecommendation(recommendation));
    }

    @Operation(summary = "Find all recommendations")
    @GetMapping("/rule")
    public List<BankRecommendationRule> getAllRecommendations() {
        return service.getAllRules();
    }

    @Operation(summary = "Delete recommendation")
    @DeleteMapping("/rule/{ruleId}")
    public void removeRecommendation(@PathVariable int ruleId) {
        service.setRecommendationRules();
        service.removeRule(ruleId);
    }

    @Operation(summary = "Find recommendation for user")
    @GetMapping("/user/{id}")
    public BankRecommendationRule getRecommendation(@PathVariable String id) {
        return service.getRuleByUserId(UUID.fromString(id));
    }
}
