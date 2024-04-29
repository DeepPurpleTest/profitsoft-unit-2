package org.example.profitsoftunit2.mapper;

import org.example.profitsoftunit2.model.dto.ProjectDto;
import org.example.profitsoftunit2.model.entity.Project;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = ERROR,
		unmappedSourcePolicy = IGNORE,
		componentModel = MappingConstants.ComponentModel.SPRING,
		uses = {MemberMapper.class, TaskMapper.class})
public interface ProjectMapper {

	@Named("simpleProjectMapper")
	@Mapping(target = "members", ignore = true)
	@Mapping(target = "tasks", ignore = true)
	Project toEntity(ProjectDto projectDto);

	@Named("fullProjectMapper")
	Project toFullEntity(ProjectDto projectDto);

	ProjectDto toDto(Project project);

	List<ProjectDto> mapAllToDto(List<Project> projects);

	@IterableMapping(qualifiedByName = "fullProjectMapper")
	List<Project> mapAllToEntity(List<ProjectDto> dtoProjects);
}
