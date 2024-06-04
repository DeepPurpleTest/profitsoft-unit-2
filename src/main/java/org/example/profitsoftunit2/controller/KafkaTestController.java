package org.example.profitsoftunit2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit2.model.dto.KafkaMessage;
import org.example.profitsoftunit2.service.impl.KafkaTestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka-test")
@RequiredArgsConstructor
public class KafkaTestController {

	private final KafkaTestService kafkaTestService;

	@PostMapping
	public String testSendMessageToKafka(@RequestBody KafkaMessage kafkaMessage)
			throws JsonProcessingException {
		kafkaTestService.sendMessage(kafkaMessage);
		return "sent";
	}
}
