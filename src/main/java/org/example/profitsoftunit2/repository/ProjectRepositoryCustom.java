package org.example.profitsoftunit2.repository;

import org.example.profitsoftunit2.model.dto.ProjectSearchDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepositoryCustom {

	List<Project> findWithFiltration(Pageable pageable, ProjectSearchDto searchDto);
}
