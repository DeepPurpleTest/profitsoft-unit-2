package org.example.profitsoftunit2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class used to represent a simplified view of project information
 * This class is typically used when returning a list of projects with basic details
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleProjectDto {

	private Long id;

	private String name;

	private String description;
}
