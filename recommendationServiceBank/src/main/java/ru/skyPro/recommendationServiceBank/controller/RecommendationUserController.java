package ru.skyPro.recommendationServiceBank.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skyPro.recommendationServiceBank.model.ClientRecommendation;
import ru.skyPro.recommendationServiceBank.service.RecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class RecommendationUserController {
    private final RecommendationService service;
    @Autowired
    public RecommendationUserController( RecommendationService service) {
        this.service = service;
    }

    @Operation(summary = "Find recommendation for user")
    @GetMapping("/user/{id}")
    public ClientRecommendation getRecommendationUser(@PathVariable String id) {
        return service.getClientRecommendationByJDBCTemplate(UUID.fromString(id));
    }
}
