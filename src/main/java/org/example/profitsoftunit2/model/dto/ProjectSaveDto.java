package org.example.profitsoftunit2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class used for creating a new project with a name and description
 * This class is intended for creating empty projects, specifying only the name and description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectSaveDto {

	@NotEmpty(message = "Name is mandatory")
	private String name;

	@NotEmpty(message = "Description is mandatory")
	private String description;
}
