package org.example.profitsoftunit2.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectSearchDto {

	@JsonProperty("project_name")
	private String projectName;

	@JsonProperty("members_ids")
	private List<Long> membersIds;

	@JsonProperty("members_names")
	private List<String> membersNames;

	private int page;

	private int size;
}
