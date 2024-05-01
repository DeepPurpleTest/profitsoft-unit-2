package org.example.profitsoftunit2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
