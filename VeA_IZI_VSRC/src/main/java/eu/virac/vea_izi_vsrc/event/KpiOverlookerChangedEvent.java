package eu.virac.vea_izi_vsrc.event;

//Message sent through Kafka when a KPI is handed over to a new overlooker.
public record KpiOverlookerChangedEvent(long kpiId, String kpiTitle, String deadline, String recipientEmail,
		String recipientName) {

}
