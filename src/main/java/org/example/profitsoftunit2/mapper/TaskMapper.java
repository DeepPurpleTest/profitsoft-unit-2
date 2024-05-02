package org.example.profitsoftunit2.mapper;

import org.example.profitsoftunit2.model.dto.TaskDto;
import org.example.profitsoftunit2.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * Class that have @Mapper annotation from mapstruct dependency
 * Generate methods for map Task entity in dto and conversely
 */
@Mapper(unmappedTargetPolicy = ERROR,
		unmappedSourcePolicy = IGNORE,
		componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
	@Mapping(target = "reporter.id", source = "reporterId")
	@Mapping(target = "assignee.id", source = "assigneeId")
	@Mapping(target = "project.id", source = "projectId")
	Task toEntity(TaskDto taskDto);

	@Mapping(target = "reporterId", source = "reporter.id")
	@Mapping(target = "assigneeId", source = "assignee.id")
	@Mapping(target = "projectId", source = "project.id")
	TaskDto toDto(Task task);

	List<TaskDto> mapAllToDto(List<Task> taskList);
}
