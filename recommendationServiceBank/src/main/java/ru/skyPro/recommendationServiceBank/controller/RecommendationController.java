package ru.skyPro.recommendationServiceBank.controller;

import org.springframework.web.bind.annotation.*;
import ru.skyPro.recommendationServiceBank.model.BankRecommendation;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Recommendation;
import ru.skyPro.recommendationServiceBank.service.CombinerQueryServiceImpl;
import ru.skyPro.recommendationServiceBank.service.RecommendationsService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationsService service;
    private final CombinerQueryServiceImpl combinerQueryService;

    public RecommendationController(RecommendationsService service, CombinerQueryServiceImpl combinerQueryService) {
        this.service = service;
        this.combinerQueryService = combinerQueryService;
    }

    @GetMapping("/{id}")
    public ClientRecommendation getRecommendations(@PathVariable("id") String id) {
        return service.getClientRecommendation(UUID.fromString(id));
    }

    @PostMapping("/rule")
    public BankRecommendation createRecommendation(@RequestParam String firstNameRule,
                                                   @RequestParam String secondNameRule,
                                                   @RequestParam String thirdNameRule) {
        return combinerQueryService.createDynamicRule(firstNameRule, secondNameRule, thirdNameRule);
    }
}
