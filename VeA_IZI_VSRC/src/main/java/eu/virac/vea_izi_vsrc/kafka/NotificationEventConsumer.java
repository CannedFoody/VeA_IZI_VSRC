package eu.virac.vea_izi_vsrc.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import eu.virac.vea_izi_vsrc.config.KafkaTopicConfig;
import eu.virac.vea_izi_vsrc.event.KpiCreatedEvent;
import eu.virac.vea_izi_vsrc.event.TaskStatusChangedEvent;
import eu.virac.vea_izi_vsrc.service.INotificationService;

@Component
public class NotificationEventConsumer {

	private static final Logger log = LoggerFactory.getLogger(NotificationEventConsumer.class);

	private final INotificationService notificationService;

	public NotificationEventConsumer(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	// Scenario 1: task status changed -> email the KPI's overlooker
	@KafkaListener(topics = KafkaTopicConfig.TASK_STATUS_TOPIC)
	public void onTaskStatusChanged(TaskStatusChangedEvent event) {
		log.info("[CONSUMER] Received task status event: {}", event);

		String subject = "Task status changed: " + event.taskTitle();
		String body = "Hello " + event.recipientName() + ",\n\n" + "The status of task \"" + event.taskTitle()
				+ "\" (KPI: " + event.kpiTitle() + ")\n" + "changed from " + event.oldStatus() + " to "
				+ event.newStatus() + ".\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}

	// Scenario 2: new KPI created -> email the KPI's overlooker
	@KafkaListener(topics = KafkaTopicConfig.KPI_CREATED_TOPIC)
	public void onKpiCreated(KpiCreatedEvent event) {
		log.info("[CONSUMER] Received KPI created event: {}", event);

		String subject = "New KPI assigned to you: " + event.kpiTitle();
		String body = "Hello " + event.recipientName() + ",\n\n" + "You have been set as the overlooker of a new KPI:\n"
				+ "Title: " + event.kpiTitle() + "\n" + "Description: " + event.kpiDescription() + "\n" + "Deadline: "
				+ event.deadline() + "\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}
}
