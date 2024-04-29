package org.example.profitsoftunit2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

	private Long id;

	@NotEmpty(message = "Name is mandatory")
	private String name;

	private String description;

	private List<TaskDto> tasks;

	private List<MemberDto> members;
}
