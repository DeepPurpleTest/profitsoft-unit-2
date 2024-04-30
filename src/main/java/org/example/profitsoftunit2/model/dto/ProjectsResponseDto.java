package org.example.profitsoftunit2.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectsResponseDto {

	private List<SimpleProjectDto> projects;

	@JsonProperty("total_pages")
	private int totalPages;
}
