package org.example.profitsoftunit2.mapper;

import org.example.profitsoftunit2.model.dto.MemberDto;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.entity.Project;
import org.example.profitsoftunit2.model.entity.Task;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * Class that have @Mapper annotation from mapstruct dependency
 * Generate methods for map Member entity in dto and conversely
 */
@Mapper(unmappedTargetPolicy = ERROR,
		unmappedSourcePolicy = IGNORE,
		componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {

	@Mapping(target = "projects", ignore = true)
	@Mapping(target = "createdTasks", ignore = true)
	@Mapping(target = "assignedTasks", ignore = true)
	Member toEntity(MemberDto memberDto);

	@Mapping(target = "projectsIds", ignore = true)
	@Mapping(target = "createdTasksIds", ignore = true)
	@Mapping(target = "assignedTasksIds", ignore = true)
	MemberDto toDto(Member member);

	default List<Long> mapProjects(Set<Project> projects) {
		return projects.stream()
				.map(Project::getId)
				.toList();
	}

	default List<Long> mapTasks(Set<Task> projects) {
		return projects.stream()
				.map(Task::getId)
				.toList();
	}

	@AfterMapping
	default void mapProjectsToMember(Member member, @MappingTarget MemberDto.MemberDtoBuilder builder) {
		builder.projectsIds(mapProjects(member.getProjects()));
		builder.assignedTasksIds(mapTasks(member.getAssignedTasks()));
		builder.createdTasksIds(mapTasks(member.getCreatedTasks()));
	}

	List<MemberDto> mapAllToDto(List<Member> members);
}
