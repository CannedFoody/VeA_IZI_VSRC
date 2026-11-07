package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a user becomes the head of a department.
public record DepartmentHeadAssignedEvent(long userId, String departmentName, String startingDate,
		String recipientEmail, String recipientName) {

}
