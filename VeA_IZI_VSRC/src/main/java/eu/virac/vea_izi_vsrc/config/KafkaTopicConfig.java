package eu.virac.vea_izi_vsrc.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	public static final String TASK_STATUS_TOPIC = "task-status-changed";
	public static final String KPI_CREATED_TOPIC = "kpi-created";
	public static final String KPI_STATUS_TOPIC = "kpi-status-changed";
	public static final String TASK_CREATED_TOPIC = "task-created";
	public static final String USER_CREATED_TOPIC = "user-created";
	public static final String DEPARTMENT_HEAD_TOPIC = "department-head-assigned";
	public static final String KPI_OVERLOOKER_TOPIC = "kpi-overlooker-changed";
	public static final String USER_ROLE_TOPIC = "user-role-changed";

	// Topics are created on startup if they don't exist yet
	@Bean
	public NewTopic taskStatusTopic() {
		return TopicBuilder.name(TASK_STATUS_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic kpiCreatedTopic() {
		return TopicBuilder.name(KPI_CREATED_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic kpiStatusTopic() {
		return TopicBuilder.name(KPI_STATUS_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic taskCreatedTopic() {
		return TopicBuilder.name(TASK_CREATED_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic userCreatedTopic() {
		return TopicBuilder.name(USER_CREATED_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic departmentHeadTopic() {
		return TopicBuilder.name(DEPARTMENT_HEAD_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic kpiOverlookerTopic() {
		return TopicBuilder.name(KPI_OVERLOOKER_TOPIC).partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic userRoleTopic() {
		return TopicBuilder.name(USER_ROLE_TOPIC).partitions(1).replicas(1).build();
	}
}
