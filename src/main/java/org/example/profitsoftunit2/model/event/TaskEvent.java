package org.example.profitsoftunit2.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Abstract class for Task events
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TaskEvent {

	/**
	 * Event type (Create, Update, Delete) for creating different types of events
	 */
	private EventType eventType;

	/**
	 * Notification type to select the desired topic
	 */
	private NotificationType notificationType;
}
