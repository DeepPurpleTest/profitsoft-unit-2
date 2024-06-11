package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.model.event.TaskEvent;
import org.example.profitsoftunit2.service.EventService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventServiceImpl implements EventService<TaskEvent> {

	private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

	@Value(value = "${kafka.topics.task}")
	private String taskMailsTopic;

	/**
	 * Method for producing events in kafka
	 */
	@Override
	public void produceEvents(List<TaskEvent> events) {
		for (TaskEvent event : events) {
			notify(event);
		}
	}

	/**
	 * Send event in kafka with determine topic by notification type
	 */
	private void notify(TaskEvent event) {
		log.info("Event to {} topic: {}", taskMailsTopic, event);
		kafkaTemplate.send(taskMailsTopic, event);
	}
}
