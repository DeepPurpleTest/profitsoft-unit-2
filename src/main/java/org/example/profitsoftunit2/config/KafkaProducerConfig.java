package org.example.profitsoftunit2.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.profitsoftunit2.model.event.TaskEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up a Kafka producer.
 * This class defines beans for creating a Kafka producer factory and a Kafka template for sending TaskEvent messages to Kafka topics.
 */
@Configuration
public class KafkaProducerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	/**
	 * Creates a Kafka producer factory for producing TaskEvent messages.
	 */
	@Bean
	public ProducerFactory<String, TaskEvent> taskEventProducerFactory() {
		Map<String, Object> configProps = new HashMap<>();

		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		JsonSerializer<TaskEvent> jsonSerializer = new JsonSerializer<>();
		jsonSerializer.setAddTypeInfo(false);

		return new DefaultKafkaProducerFactory<>(
				configProps,
				new StringSerializer(),
				jsonSerializer);
	}

	/**
	 * Creates a Kafka template for sending TaskEvent messages to Kafka topics.
	 */
	@Bean
	public KafkaTemplate<String, TaskEvent> kafkaTemplate() {
		return new KafkaTemplate<>(taskEventProducerFactory());
	}
}
