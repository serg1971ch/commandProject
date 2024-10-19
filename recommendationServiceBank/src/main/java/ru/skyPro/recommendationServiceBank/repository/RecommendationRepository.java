package ru.skyPro.recommendationServiceBank.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Recommendation;

import java.util.UUID;

public interface RecommendationRepository extends ListCrudRepository<Recommendation, Long> {
    Recommendation findByProductName(String productName);
}
