package eu.virac.vea_izi_vsrc.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import eu.virac.vea_izi_vsrc.config.KafkaTopicConfig;
import eu.virac.vea_izi_vsrc.event.KpiCreatedEvent;
import eu.virac.vea_izi_vsrc.event.TaskStatusChangedEvent;

@Component
public class NotificationEventProducer {

	private static final Logger log = LoggerFactory.getLogger(NotificationEventProducer.class);

	private final KafkaTemplate<String, Object> kafkaTemplate;

	public NotificationEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishTaskStatusChanged(TaskStatusChangedEvent event) {
		send(KafkaTopicConfig.TASK_STATUS_TOPIC, String.valueOf(event.taskId()), event);
	}

	public void publishKpiCreated(KpiCreatedEvent event) {
		send(KafkaTopicConfig.KPI_CREATED_TOPIC, String.valueOf(event.kpiId()), event);
	}

	private void send(String topic, String key, Object event) {
		try {
			kafkaTemplate.send(topic, key, event).whenComplete((result, ex) -> {
				if (ex != null) {
					log.error("[PRODUCER] Failed to send to '{}': {}", topic, ex.getMessage());
				} else {
					log.info("[PRODUCER] Sent to '{}' (partition {}, offset {}): {}", topic,
							result.getRecordMetadata().partition(), result.getRecordMetadata().offset(), event);
				}
			});
		} catch (Exception e) {
			// Saving the task/KPI must still succeed even if Kafka is down
			log.error("[PRODUCER] Could not send to '{}': {}", topic, e.getMessage());
		}
	}
}
