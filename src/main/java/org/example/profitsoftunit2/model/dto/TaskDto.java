package org.example.profitsoftunit2.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

	private Long id;

	@NotEmpty(message = "Name is mandatory")
	private String name;

	private String description;

	@JsonProperty("project_id")
	@NotNull(message = "Project ID must not be null")
	@Positive(message = "Project ID must be > 0")
	private Long projectId;

	@JsonProperty("reporter_id")
	@NotNull(message = "Reporter ID must not be null")
	@Positive(message = "Reporter ID must be > 0")
	private Long reporterId;

	@JsonProperty("assignee_id")
	private Long assigneeId;
}
