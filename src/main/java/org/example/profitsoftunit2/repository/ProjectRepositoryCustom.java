package org.example.profitsoftunit2.repository;

import org.example.profitsoftunit2.model.dto.ProjectsSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepositoryCustom {

	List<Project> findWithFiltrationAndPagination(ProjectsSearchDto searchDto);

	List<Project> findWithFiltration(ProjectsSearchDto searchDto);
}
