package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a KPI's status changes.
public record KpiStatusChangedEvent(long kpiId, String kpiTitle, String oldStatus, String newStatus,
		String recipientEmail, String recipientName) {

}
