package org.example.profitsoftunit2.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit2.model.dto.ProjectSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.repository.ProjectRepositoryCustom;
import org.example.profitsoftunit2.service.strategy.FilterStrategy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

	private final EntityManager em;

	@Override
	public List<Project> findWithFiltration(Pageable pageable, ProjectSearchDto searchDto) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Project> cq = cb.createQuery(Project.class);

		Root<Project> project = cq.from(Project.class);
		List<Predicate> predicates = new ArrayList<>();

		for (FilterStrategy strategy : createFilterStrategyList(searchDto)) {
			Predicate predicate = strategy.createPredicate(project, cb);
			if (predicate != null) {
				predicates.add(predicate);
			}
		}

		cq.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Project> typedQuery = em.createQuery(cq);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());

		return em.createQuery(cq).getResultList();
	}

	private List<FilterStrategy> createFilterStrategyList(ProjectSearchDto searchDto) {
		List<FilterStrategy> predicates = new ArrayList<>();
		predicates.add((root, cb) -> equalPredicate(cb, root.get("name"), searchDto.getProjectName()));
		predicates.add((root, cb) -> cb.and(memberIdsPredicate(root, searchDto.getMembersIds()).toArray(new Predicate[0])));
		predicates.add((root, cb) -> cb.and(memberNamesPredicate(root, searchDto.getMembersNames()).toArray(new Predicate[0])));

		return predicates;
	}

	private Predicate equalPredicate(CriteriaBuilder cb, Path<String> path, String value) {
		return value != null ?
				cb.equal(path, value) : null;
	}

	private List<Predicate> memberIdsPredicate(Root<Project> root, List<Long> memberIds) {
		return createMemberPredicate(root, memberIds, "id");
	}

	private List<Predicate> memberNamesPredicate(Root<Project> root, List<String> memberNames) {
		return createMemberPredicate(root, memberNames, "name");
	}

	private List<Predicate> createMemberPredicate(Root<Project> root, List<?> memberList, String attributeName) {
		List<Predicate> predicates = new ArrayList<>();
		if (memberList != null && !memberList.isEmpty()) {
			for (Object member : memberList) {
				predicates.add(root.join("members").get(attributeName).in(member));
			}
		}
		return predicates;
	}
}
