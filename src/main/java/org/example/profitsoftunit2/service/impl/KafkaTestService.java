package org.example.profitsoftunit2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.profitsoftunit2.model.dto.KafkaMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaTestService {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public void sendMessage(KafkaMessage kafkaMessage) throws JsonProcessingException {
		kafkaTemplate.send("mail", objectMapper.writeValueAsString(kafkaMessage));
	}
}
