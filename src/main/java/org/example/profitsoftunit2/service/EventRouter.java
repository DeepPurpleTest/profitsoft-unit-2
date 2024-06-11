package org.example.profitsoftunit2.service;

/**
 * Interface for check type of notification and choose topic where send event
 */
public interface EventRouter<T> {

	String determineTopic(T event);
}
