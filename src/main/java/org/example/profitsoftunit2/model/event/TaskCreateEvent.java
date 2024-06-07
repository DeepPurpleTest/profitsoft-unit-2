package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateEvent {

	private TaskInfo task;

	private ReceiverInfo receiver;

	private LocalDateTime createdAt;
}
