package eu.virac.vea_izi_vsrc.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import eu.virac.vea_izi_vsrc.config.KafkaTopicConfig;
import eu.virac.vea_izi_vsrc.event.DepartmentHeadAssignedEvent;
import eu.virac.vea_izi_vsrc.event.KpiCreatedEvent;
import eu.virac.vea_izi_vsrc.event.KpiOverlookerChangedEvent;
import eu.virac.vea_izi_vsrc.event.KpiStatusChangedEvent;
import eu.virac.vea_izi_vsrc.event.TaskCreatedEvent;
import eu.virac.vea_izi_vsrc.event.TaskStatusChangedEvent;
import eu.virac.vea_izi_vsrc.event.UserCreatedEvent;
import eu.virac.vea_izi_vsrc.event.UserRoleChangedEvent;
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

	// Scenario 3: KPI status changed -> email the KPI's creator
	@KafkaListener(topics = KafkaTopicConfig.KPI_STATUS_TOPIC)
	public void onKpiStatusChanged(KpiStatusChangedEvent event) {
		log.info("[CONSUMER] Received KPI status event: {}", event);

		String subject = "KPI status changed: " + event.kpiTitle();
		String body = "Hello " + event.recipientName() + ",\n\n" + "The status of KPI \"" + event.kpiTitle() + "\"\n"
				+ "changed from " + event.oldStatus() + " to " + event.newStatus() + ".\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}

	// Scenario 4: new task created -> email the KPI's overlooker
	@KafkaListener(topics = KafkaTopicConfig.TASK_CREATED_TOPIC)
	public void onTaskCreated(TaskCreatedEvent event) {
		log.info("[CONSUMER] Received task created event: {}", event);

		String subject = "New task added to KPI: " + event.kpiTitle();
		String body = "Hello " + event.recipientName() + ",\n\n" + "A new task was added to the KPI \""
				+ event.kpiTitle() + "\":\n" + "Title: " + event.taskTitle() + "\n" + "Description: "
				+ event.taskDescription() + "\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}

	// Scenario 5: new user -> welcome email
	@KafkaListener(topics = KafkaTopicConfig.USER_CREATED_TOPIC)
	public void onUserCreated(UserCreatedEvent event) {
		log.info("[CONSUMER] Received user created event: {}", event);

		String subject = "Welcome to the system, " + event.recipientName();
		String body = "Hello " + event.recipientName() + ",\n\n" + "Your account has been created.\n" + "Role: "
				+ event.role() + "\n" + "Department: " + event.departmentName() + "\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}

	// Scenario 6: department head assigned -> email the new head
	@KafkaListener(topics = KafkaTopicConfig.DEPARTMENT_HEAD_TOPIC)
	public void onDepartmentHeadAssigned(DepartmentHeadAssignedEvent event) {
		log.info("[CONSUMER] Received department head event: {}", event);

		String subject = "You are now the head of " + event.departmentName();
		String body = "Hello " + event.recipientName() + ",\n\n"
				+ "You have been assigned as the head of the department \"" + event.departmentName() + "\".\n"
				+ "Starting date: " + event.startingDate() + "\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}

	// Scenario 7: KPI overlooker changed -> email the new overlooker
	@KafkaListener(topics = KafkaTopicConfig.KPI_OVERLOOKER_TOPIC)
	public void onKpiOverlookerChanged(KpiOverlookerChangedEvent event) {
		log.info("[CONSUMER] Received KPI overlooker event: {}", event);

		String subject = "KPI handed over to you: " + event.kpiTitle();
		String body = "Hello " + event.recipientName() + ",\n\n" + "You are now the overlooker of the KPI \""
				+ event.kpiTitle() + "\".\n" + "Deadline: " + event.deadline() + "\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}

	// Scenario 8: user role changed -> email the user
	@KafkaListener(topics = KafkaTopicConfig.USER_ROLE_TOPIC)
	public void onUserRoleChanged(UserRoleChangedEvent event) {
		log.info("[CONSUMER] Received user role event: {}", event);

		String subject = "Your role has changed";
		String body = "Hello " + event.recipientName() + ",\n\n" + "Your role changed from " + event.oldRole() + " to "
				+ event.newRole() + ".\n";

		notificationService.sendNotification(event.recipientEmail(), subject, body);
	}
}
