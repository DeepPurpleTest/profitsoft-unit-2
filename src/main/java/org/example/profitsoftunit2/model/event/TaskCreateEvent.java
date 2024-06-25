package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * A class that is created to notify an external service that a task has been created
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TaskCreateEvent extends TaskEvent {

	private TaskInfo task;

	private ReceiverInfo receiver;

	private Instant createdAt;
}

