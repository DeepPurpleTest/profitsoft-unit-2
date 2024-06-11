package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit2.model.entity.Member;
import org.example.profitsoftunit2.model.entity.Task;
import org.example.profitsoftunit2.model.event.EventType;
import org.example.profitsoftunit2.model.event.NotificationType;
import org.example.profitsoftunit2.model.event.ReceiverInfo;
import org.example.profitsoftunit2.model.event.TaskCreateEvent;
import org.example.profitsoftunit2.model.event.TaskEvent;
import org.example.profitsoftunit2.model.event.TaskInfo;
import org.example.profitsoftunit2.service.EventBuilder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskEventBuilderImpl implements EventBuilder<Task, TaskEvent> {

	@Override
	public List<TaskEvent> buildEvents(Task entity, EventType type) {
		List<TaskEvent> events = new ArrayList<>();

		if (type.equals(EventType.CREATE)) {
			buildCreateEvents(entity, events);
		}

		return events;
	}

	private void buildCreateEvents(Task entity, List<TaskEvent> events) {
		events.add(buildCreateEvent(entity, entity.getReporter(), NotificationType.REPORTER_NOTIFICATION));

		if (entity.getAssignee() != null) {
			events.add(buildCreateEvent(entity, entity.getAssignee(), NotificationType.ASSIGNEE_NOTIFICATION));
		}
	}

	private TaskCreateEvent buildCreateEvent(Task task, Member receiver, NotificationType type) {
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
				.createdAt(Instant.now())
				.notificationType(type)
				.build();
	}
}


