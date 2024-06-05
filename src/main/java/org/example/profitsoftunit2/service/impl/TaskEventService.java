package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.model.event.TaskCreateEvent;
import org.example.profitsoftunit2.model.entity.Task;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventService {

	private final KafkaTemplate<String, TaskCreateEvent> kafkaTemplate;

	public void produceCreateEvent(Task task) {
		TaskCreateEvent event = TaskCreateEvent.builder()
				.taskName(task.getName())
				.reporterName(task.getReporter().getName())
				.assigneeEmail(task.getAssignee().getEmail())
				.build();

		log.info("Event: {}", event);
		kafkaTemplate.send("task-create", event);
	}
}
