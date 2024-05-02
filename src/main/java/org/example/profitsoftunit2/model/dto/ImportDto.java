package org.example.profitsoftunit2.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that used for response when accessing /api/projects/upload endpoint
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImportDto {

	@JsonProperty("successfully_saved")
	private int successfullySaved;

	private int failed;
}
