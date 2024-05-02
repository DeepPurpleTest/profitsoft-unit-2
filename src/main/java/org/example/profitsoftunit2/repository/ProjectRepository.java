package org.example.profitsoftunit2.repository;

import org.example.profitsoftunit2.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project repository that extends ProjectRepositoryCustom for custom filter methods
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
}
