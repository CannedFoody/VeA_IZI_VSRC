package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a new user is created (welcome email).
public record UserCreatedEvent(long userId, String role, String departmentName, String recipientEmail,
		String recipientName) {

}
