package eu.virac.vea_izi_vsrc.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	public static final String TASK_STATUS_TOPIC = "task-status-changed";
	public static final String KPI_CREATED_TOPIC = "kpi-created";

	// Topics are created on startup if they don't exist yet
	@Bean
	public NewTopic taskStatusTopic() {
		return TopicBuilder.name(TASK_STATUS_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic kpiCreatedTopic() {
		return TopicBuilder.name(KPI_CREATED_TOPIC).partitions(1).replicas(1).build();
	}
}
