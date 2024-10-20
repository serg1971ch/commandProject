package ru.skyPro.recommendationServiceBank.repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Recommendation;
import ru.skyPro.recommendationServiceBank.model.rulesEntity.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    Rule findByQueryName(String name);

    @Transactional
    @Modifying
    @Query("update Rule r set r.recommendation = ?1 where r.id = ?2")
    void updateQuery(Recommendation recommendation, Long id);
}
