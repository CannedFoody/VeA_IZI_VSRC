package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a new KPI is created.
public record KpiCreatedEvent(long kpiId, String kpiTitle, String kpiDescription, String deadline,
		String recipientEmail, String recipientName) {
}
