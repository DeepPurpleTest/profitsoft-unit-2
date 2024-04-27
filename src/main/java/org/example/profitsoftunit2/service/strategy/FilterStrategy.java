package org.example.profitsoftunit2.service.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.profitsoftunit2.model.entity.Project;

@FunctionalInterface
public interface FilterStrategy {
	Predicate createPredicate(Root<Project> projectRoot, CriteriaBuilder criteriaBuilder);
}
