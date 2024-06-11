package org.example.profitsoftunit2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.profitsoftunit2.model.event.TaskEvent;
import org.example.profitsoftunit2.service.EventRouter;
import org.example.profitsoftunit2.service.EventService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskEventServiceImpl implements EventService<TaskEvent> {

	private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

	private final EventRouter<TaskEvent> router;

	@Override
	public void produceEvents(List<TaskEvent> events) {
		for (TaskEvent event : events) {
			notify(event);
		}
	}

	private void notify(TaskEvent event) {
		String topic = router.determineTopic(event);
		log.info("Event to {} topic: {}", topic, event);
		kafkaTemplate.send(topic, event);
	}
}
