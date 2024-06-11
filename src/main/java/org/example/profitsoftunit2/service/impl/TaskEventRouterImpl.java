package org.example.profitsoftunit2.service.impl;

import org.example.profitsoftunit2.model.event.TaskEvent;
import org.example.profitsoftunit2.service.EventRouter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaskEventRouterImpl implements EventRouter<TaskEvent> {

	@Value(value = "${kafka.topics.reporterTopic}")
	private String reporterTopic;

	@Value(value = "${kafka.topics.assigneeTopic}")
	private String assigneeTopic;

	/**
	 * Check event type notify and choose topic
	 */
	@Override
	public String determineTopic(TaskEvent event) {
		return switch (event.getNotificationType()) {
			case ASSIGNEE_NOTIFICATION -> assigneeTopic;
			case REPORTER_NOTIFICATION -> reporterTopic;
		};
	}
}
