package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a new task is created.
public record TaskCreatedEvent(long taskId, String taskTitle, String taskDescription, String kpiTitle,
		String recipientEmail, String recipientName) {

}
