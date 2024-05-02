package org.example.profitsoftunit2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.repository.ProjectRepositoryCustom;
import org.example.profitsoftunit2.service.strategy.FilterStrategy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom repository implementation using Criteria API
 * This repository is used when we need to find Project data with filtration
 */
@Repository
@RequiredArgsConstructor
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private final EntityManager entityManager;


	/**
	 * Finds projects with filtration and pagination based on the provided searchDto
	 * */
	@Override
	public List<Project> findWithFiltrationAndPagination(ProjectsSearchDto searchDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);
		Root<Project> project = cq.from(Project.class);

		List<Predicate> predicates = createPredicates(cb, project, searchDto);
		cq.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Project> typedQuery = createPaginatedQuery(cq, searchDto.getOffset(), searchDto.getPageSize());
		return typedQuery.getResultList();
	}

	/**
	 * Finds projects with filtration based on the provided searchDto
	 */
	@Override
	public List<Project> findWithFiltration(ProjectsSearchDto searchDto) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);
		Root<Project> project = cq.from(Project.class);

		List<Predicate> predicates = createPredicates(cb, project, searchDto);
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).getResultList();
	}

	private TypedQuery<Project> createPaginatedQuery(CriteriaQuery<Project> cq, int offset, int pageSize) {
		TypedQuery<Project> typedQuery = entityManager.createQuery(cq);
		typedQuery.setFirstResult(offset);
		typedQuery.setMaxResults(pageSize);
		return typedQuery;
	}

	private List<Predicate> createPredicates(CriteriaBuilder cb, Root<Project> project, ProjectsSearchDto searchDto) {
		List<Predicate> predicates = new ArrayList<>();
		for (FilterStrategy strategy : createFilterStrategyList(searchDto)) {
			Predicate predicate = strategy.createPredicate(project, cb);
			predicates.add(predicate);
		}

		return predicates;
	}

	private List<FilterStrategy> createFilterStrategyList(ProjectsSearchDto searchDto) {
		List<FilterStrategy> predicates = new ArrayList<>();

		predicates.add((root, cb) -> equalPredicate(cb, root.get("name"), searchDto.getProjectName()));
		predicates.add((root, cb) -> cb.and(createMemberPredicate(root, searchDto.getMembersIds(), "id")));
		predicates.add((root, cb) -> cb.and(createMemberPredicate(root, searchDto.getMembersNames(), "name")));

		return predicates;
	}

	private Predicate equalPredicate(CriteriaBuilder cb, Path<String> path, String value) {
		return value != null ? cb.equal(path, value) : cb.conjunction();
	}

	private Predicate[] createMemberPredicate(Root<Project> root, List<?> memberList, String attributeName) {
		if (memberList == null || memberList.isEmpty()) {
			return new Predicate[0];
		}

		List<Predicate> predicates = new ArrayList<>();
		for (Object member : memberList) {
			predicates.add(root.join("members").get(attributeName).in(member));
		}

		return predicates.toArray(new Predicate[0]);
	}
}
