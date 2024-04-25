package org.example.profitsoftunit2.mapper;

import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = ERROR,
		unmappedSourcePolicy = IGNORE,
		componentModel = MappingConstants.ComponentModel.SPRING,
		uses = {MemberMapper.class, TaskMapper.class})
public interface ProjectMapper {

//	@Mapping(target = "id", ignore = true)
//	@Mapping(target = "members", ignore = true)
//	@Mapping(target = "tasks", ignore = true)
	Project toEntity(ProjectDto projectDto);

	ProjectDto toDto(Project project);

	List<ProjectDto> mapAllToDto(List<Project> projects);

	List<Project> mapAllToEntity(List<ProjectDto> dtoProjects);
}
