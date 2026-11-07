package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a user's role changes.
public record UserRoleChangedEvent(long userId, String oldRole, String newRole, String recipientEmail,
		String recipientName) {

}
