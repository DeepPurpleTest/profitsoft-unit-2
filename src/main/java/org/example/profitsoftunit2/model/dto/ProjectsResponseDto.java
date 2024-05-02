package org.example.profitsoftunit2.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class used for representing the response to the /api/projects/_list endpoint
 * Contains a list of simple project information and the total number of pages for pagination
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectsResponseDto {
	/**
	 * List of simple project information
	 */
	private List<SimpleProjectDto> projects;

	/**
	 * Total number of pages for pagination
	 */
	@JsonProperty("total_pages")
	private int totalPages;
}
