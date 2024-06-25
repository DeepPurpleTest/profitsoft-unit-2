package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for transport task data
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskInfo {

	String projectName;

	String taskName;

	String taskDescription;
}
