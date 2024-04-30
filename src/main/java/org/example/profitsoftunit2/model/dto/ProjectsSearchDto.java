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
public class ProjectsSearchDto {

	@JsonProperty("project_name")
	private String projectName;

	@JsonProperty("members_ids")
	private List<Long> membersIds;

	@JsonProperty("members_names")
	private List<String> membersNames;

	private int offset;

	private int pageSize;
}
