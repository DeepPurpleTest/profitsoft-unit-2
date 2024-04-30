package org.example.profitsoftunit2.model.dto;

import jakarta.validation.constraints.Email;
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
public class MemberDto {

	private Long id;

	@NotEmpty(message = "Name is mandatory")
	private String name;

	@Email
	private String email;

	private List<Long> projectsIds;

	private List<Long> createdTasksIds;

	private List<Long> assignedTasksIds;
}
