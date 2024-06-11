package org.example.profitsoftunit2.service;

import java.util.List;

/**
 * Service for producing different types of events
 */
public interface EventService<T> {

	void produceEvents(List<T> events);
}
