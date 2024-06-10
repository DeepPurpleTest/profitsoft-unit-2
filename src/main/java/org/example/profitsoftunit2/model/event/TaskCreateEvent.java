package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateEvent {

	private TaskInfo task;

	private ReceiverInfo receiver;

	private Instant createdAt;
}
