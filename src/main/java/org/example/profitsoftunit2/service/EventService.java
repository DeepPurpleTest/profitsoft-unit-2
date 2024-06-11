package org.example.profitsoftunit2.service;

import java.util.List;

public interface EventService<T> {

	void produceEvents(List<T> events);
}
