package ru.skyPro.recommendationServiceBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skyPro.recommendationServiceBank.model.BankRecommendationRule;
import ru.skyPro.recommendationServiceBank.service.RecommendationService;

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
        return ResponseEntity.ok(recommendationService.addRecommendation(recommendation));
    }

//    @GetMapping("/{id}")
//    public ClientRecommendation getRecommendations(@PathVariable("id") String id) {
//        return service.getClientRecommendationByJDBCtemplate(UUID.fromString(id));
//    }
//
//    @PostMapping
//    public BankRecommendation getCreditRecommendationsForUser()  {
//        return service.getCreditRecommendations();
//    }
}
