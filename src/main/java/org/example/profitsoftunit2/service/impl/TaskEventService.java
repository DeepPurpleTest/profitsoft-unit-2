package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.event.ReceiverInfo;
import org.example.profitsoftunit2.model.event.TaskCreateEvent;
import org.example.profitsoftunit2.model.entity.Task;
import org.example.profitsoftunit2.model.event.TaskInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventService {

	private final KafkaTemplate<String, TaskCreateEvent> kafkaTemplate;

	public void produceNotifyEvent(Task task) {
		reporterNotify(task);

		if(task.getAssignee() != null) {
			assigneeNotify(task);
		}
	}
	public void reporterNotify(Task task) {
		TaskCreateEvent event = buildEvent(task, task.getReporter());

		log.info("Event to reporter-mails topic: {}", event);
		kafkaTemplate.send("reporter-mails", event);
	}

	public void assigneeNotify(Task task) {
		TaskCreateEvent event = buildEvent(task, task.getAssignee());

		log.info("Event to assignee-mails topic: {}", event);
		kafkaTemplate.send("assignee-mails", event);
	}

	private TaskCreateEvent buildEvent(Task task, Member receiver) {
		return TaskCreateEvent.builder()
				.task(TaskInfo.builder()
						.projectName(task.getProject().getName())
						.taskName(task.getName())
						.taskDescription(task.getDescription())
						.build())
				.receiver(ReceiverInfo.builder()
						.name(receiver.getName())
						.email(receiver.getEmail())
						.build())
				.createdAt(LocalDateTime.now())
				.build();
	}
}
