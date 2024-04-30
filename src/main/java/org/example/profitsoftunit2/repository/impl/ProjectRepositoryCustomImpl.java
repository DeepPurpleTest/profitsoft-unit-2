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

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private final EntityManager em;

	//TODO refactor methods
	@Override
	public List<Project> findWithFiltrationAndPagination(ProjectsSearchDto searchDto) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);
		Root<Project> project = cq.from(Project.class);

		List<Predicate> predicates = createPredicates(cb, project, searchDto);
		cq.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Project> typedQuery = createPaginatedQuery(cq, searchDto.getOffset(), searchDto.getPageSize());
		return typedQuery.getResultList();
	}

	@Override
	public List<Project> findWithFiltration(ProjectsSearchDto searchDto) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);
		Root<Project> project = cq.from(Project.class);

		List<Predicate> predicates = createPredicates(cb, project, searchDto);
		cq.where(predicates.toArray(new Predicate[0]));

		return em.createQuery(cq).getResultList();
	}

	private TypedQuery<Project> createPaginatedQuery(CriteriaQuery<Project> cq, int offset, int pageSize) {
		TypedQuery<Project> typedQuery = em.createQuery(cq);
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
		predicates.add((root, cb) -> cb.and(memberIdsPredicate(root, searchDto.getMembersIds())));
		predicates.add((root, cb) -> cb.and(memberNamesPredicate(root, searchDto.getMembersNames())));

		return predicates;
	}

	private Predicate equalPredicate(CriteriaBuilder cb, Path<String> path, String value) {
		return value != null ? cb.equal(path, value) : cb.conjunction();
	}

	private Predicate[] memberIdsPredicate(Root<Project> root, List<Long> memberIds) {
		return createMemberPredicate(root, memberIds, "id");
	}

	private Predicate[] memberNamesPredicate(Root<Project> root, List<String> memberNames) {
		return createMemberPredicate(root, memberNames, "name");
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
