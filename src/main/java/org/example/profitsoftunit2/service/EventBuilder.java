package org.example.profitsoftunit2.service;

import org.example.profitsoftunit2.model.event.EventType;

import java.util.List;

public interface EventBuilder<T, E> {
	List<E> buildEvents(T entity, EventType type);
}
