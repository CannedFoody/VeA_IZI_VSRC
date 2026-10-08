package eu.virac.vea_izi_vsrc.event;

/**
 * Message sent through Kafka when a task's status changes. Plain data only (no
 * JPA entities), so it serializes cleanly to JSON.
 */
public record TaskStatusChangedEvent(long taskId, String taskTitle, String oldStatus, String newStatus, String kpiTitle,
		String recipientEmail, String recipientName) {
}
