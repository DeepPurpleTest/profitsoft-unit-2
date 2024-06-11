package org.example.profitsoftunit2.service;

public interface EventRouter<T> {

	String determineTopic(T event);
}
