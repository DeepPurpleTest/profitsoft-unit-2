package org.example.profitsoftunit2.service.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.profitsoftunit2.model.entity.Project;

/**
 * Functional interface for creating predicates used in filtering operations.
 */
@FunctionalInterface
public interface FilterStrategy {

	/**
	 * Creates a predicate based on the provided project root and criteria builder.
	 */
	Predicate createPredicate(Root<Project> projectRoot, CriteriaBuilder criteriaBuilder);
}
