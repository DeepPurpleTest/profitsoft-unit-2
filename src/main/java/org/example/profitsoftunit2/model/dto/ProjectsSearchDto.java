package org.example.profitsoftunit2.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class used for searching with filtration criteria
 * Supports filtering by three fields: projectName, membersIds, membersNames
 * All filters are applied using the "AND" query logic
 * For example, a project with a specified name and not containing all specified member IDs will not be returned
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectsSearchDto {

	/**
	 * Filter by project name
	 */
	@JsonProperty("project_name")
	private String projectName;

	/**
	 * Filter by member IDs
	 */
	@JsonProperty("members_ids")
	private List<Long> membersIds;

	/**
	 * Filter by member names
	 */
	@JsonProperty("members_names")
	private List<String> membersNames;

	/**
	 * Offset for pagination
	 */
	private int offset;

	/**
	 * Page size for pagination
	 */
	private int pageSize;
}
