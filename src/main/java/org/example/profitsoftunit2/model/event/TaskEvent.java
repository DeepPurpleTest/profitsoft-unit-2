package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TaskEvent {

	private EventType eventType;

	private NotificationType notificationType;
}
