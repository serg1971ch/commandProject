package ru.skyPro.recommendationServiceBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;

    }

    @Operation(summary = "Add recommendation")
    @PostMapping("/rule")
    public ResponseEntity<BankRecommendationRule> addRecommendation() {
        return ResponseEntity.ok(service.createDynamicRule());
    }

    @Operation(summary = "Find all recommendations")
    @GetMapping("/rule")
    public List<BankRecommendationRule> getAllRecommendations() {
        return service.getAllRules();
    }

    @Operation
    @PostMapping("/rule/<rule_id>")
    public void removeRecommendation(@RequestParam int rule_id) {
        service.setRecommendationRules();
        service.removeRule(rule_id);
    }
}
