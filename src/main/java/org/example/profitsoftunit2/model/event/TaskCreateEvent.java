package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateEvent {

	private String taskName;

	private String reporterName;

	private String assigneeEmail;
}