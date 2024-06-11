package org.example.profitsoftunit2.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${kafka.topics.reporterTopic}")
	private String reporterTopic;

	@Value(value = "${kafka.topics.assigneeTopic}")
	private String assigneeTopic;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic reporterMailsTopic() {
		return new NewTopic(reporterTopic, 1, (short) 1);
	}

	@Bean
	public NewTopic assigneeMailsTopic() {
		return new NewTopic(assigneeTopic, 1, (short) 1);
	}

}
