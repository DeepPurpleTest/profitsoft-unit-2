package org.example.profitsoftunit2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a response while working with the Project entity
 * Contains nested DTOs for detailed response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

	private Long id;

	@NotEmpty(message = "Name is mandatory")
	private String name;

	@NotEmpty(message = "Description is mandatory")
	private String description;

	/**
	 * List of tasks on project
	 */
	private List<TaskDto> tasks = new ArrayList<>();

	/**
	 * List of members associated with the project
	 * Updated during entity update
	 * When updating the entity, any added or removed members will be updated accordingly
	 * For example, if an ID of a member is added to this list, they will be added to the project upon update
	 * Conversely, if the ID is removed, they will be removed from the project
	 */
	private List<MemberDto> members = new ArrayList<>();
}
