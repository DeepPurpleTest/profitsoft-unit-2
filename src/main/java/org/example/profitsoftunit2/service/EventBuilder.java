package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.event.EventType;

import java.util.List;

/**
 * Interface for creating new types of events
 */
public interface EventBuilder<T, E> {

	/**
	 * Base method for create event from created/update/deleted entity
	 */
	List<E> buildEvents(T entity, EventType type);
}
