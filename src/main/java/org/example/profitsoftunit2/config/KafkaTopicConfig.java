package org.example.profitsoftunit2.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for defining Kafka topics.
 * This class declares beans for creating KafkaAdmin and NewTopic instances used to manage Kafka topics.
 */
@Configuration
public class KafkaTopicConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${kafka.topics.task}")
	private String taskMailsTopic;

	/**
	 * Creates a KafkaAdmin instance for administering Kafka topics.
	 */
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	/**
	 * Defines a NewTopic bean for the reporter mails topic.
	 */
	@Bean
	public NewTopic taskMailsTopic() {
		return new NewTopic(taskMailsTopic, 1, (short) 1);
	}

}
